package at.ac.tuwien.infosys.aic13.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import at.ac.tuwien.infosys.aic13.Utils;

public class HibernateDaoImpl implements GenericDao {

	/** The Constant MAX_RESULTS_PER_LIST. */
	private static final int MAX_RESULTS_PER_LIST = 500;
	
	/** The logger. */
	private static final Logger logger = LoggerFactory.getLogger(HibernateDaoImpl.class);

	/** The session factory. */
	@Autowired(required=true)
	private SessionFactory sessionFactory;

	/**
	 * Instantiates a new hibernate dao.
	 *
	 */
	public HibernateDaoImpl() {

	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#create(therapygroup.util.DTO)
	 */
	@Override
	public <T extends DTO> void create(T element) {
		if(sessionFactory.getCurrentSession().contains(element)) throw new DaoException("Element already exists in session!");
		sessionFactory.getCurrentSession().save(element);
		//		sessionFactory.getCurrentSession().refresh(element);
		logger.info("saved " + element.toString());
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#read(therapygroup.util.DTO)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends DTO> T read(T element) {
		if(element == null){
			throw new DaoException("Element must not be null. (TOM3NI)");
		}
		Object x = sessionFactory.getCurrentSession().get(element.getClass(), element.getId());
		if(x == null){
			logger.error("Could not read element by ID. (OJ3W9B). Class: "+element.getClass().getSimpleName()+", ID:"+element.getId());
			throw new DaoException("Could not read element by ID. (OJ3W9B). Class: "+element.getClass().getSimpleName()+", ID:"+element.getId());
		}
		return (T)x;
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#findByExample(therapygroup.util.DTO)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends DTO> List<T> findByExample(T example){
		return findByExamples(example);
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#findByExamples(T[])
	 */
	@SuppressWarnings("unchecked")
	public <T extends DTO> List<T> findByExamples(T ... examples) throws DaoException{
		if(examples == null || examples.length == 0) return null;
		Criteria c = sessionFactory.getCurrentSession().createCriteria(examples[0].getClass());
		Criterion crit = null;
		for(T elem : examples){

			Example ex = Example.create(elem);
			ex = ex.excludeZeroes();
			ex = ex.enableLike();
			ex = ex.ignoreCase();

			if(crit == null){
				crit = ex;
			}else{
				crit = Restrictions.or(crit, ex);
			}
		}
		c.add(crit);
		return findByCriteria(c, 0);
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#findByCriteria(org.hibernate.Criteria)
	 */
	/**
	 * Find by criteria.
	 *
	 * @param <T> the generic type
	 * @param criteria the criteria
	 * @param lazyLoadLevels the lazy load levels
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	@SuppressWarnings("unchecked")
	private <T extends DTO> List<T> findByCriteria(Criteria criteria, int lazyLoadLevels) throws DaoException {
		try{
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<T> ret = criteria.list();
			Utils.lazyLoad(ret, lazyLoadLevels);
			return ret;
		}catch(Exception e){
			throw new DaoException(e);
		}

	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#read(therapygroup.util.DTO, java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends DTO> T read(T element, Long id) throws DaoException {
		if(element == null) return null;
		return ((T) this.read(element.getClass(), id));
	}	

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#readAll(java.lang.Class)
	 */
	@Override
	public <T extends DTO> List<T> readAll(Class<T> clazz) {
		return readLimit(clazz, 500);
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#readLimit(java.lang.Class, int)
	 */
	@Override
	public <T extends DTO> List<T> readLimit(Class<T> clazz, int maxResults) throws DaoException {
		return readLimit(clazz, maxResults, 0);
	}
	
	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#readLimit(java.lang.Class, int, int)
	 */
	@Override
	public <T extends DTO> List<T> readLimit(Class<T> clazz, int maxResults, int lazyLoadLevels) throws DaoException {
		Query q = sessionFactory.getCurrentSession().createQuery("from " + clazz.getSimpleName());
		q.setMaxResults(maxResults);
		@SuppressWarnings("unchecked")
		List<T> ret = q.list();
		Utils.lazyLoad(ret, lazyLoadLevels);
		return ret;
	}

	/* (non-Javadoc)
	 * @see therapygroup.dao.GenericDao#update(therapygroup.dto.DTO)
	 */
	@Override
	public <T extends DTO> void update(T element) throws DaoException  {	
		sessionFactory.getCurrentSession().update(element);	
		logger.info("updated " + element.toString());
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#delete(therapygroup.util.DTO)
	 */
	@Override
	public <T extends DTO> void delete(T element) throws DaoException {
		try{
			sessionFactory.getCurrentSession().delete(element);
			logger.info("deleted " + element.toString());
		}catch(Exception e){
			throw new DaoException(e);
		}

	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#simpleQuery(therapygroup.util.DTO, org.hibernate.criterion.Criterion, org.hibernate.criterion.Order)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends DTO> List<T> simpleQuery(T element, Criterion criterion, Order order) {
		return  (List<T>) this.simpleQuery(element.getClass(), criterion, order);
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#simpleQuery(java.lang.Class, org.hibernate.criterion.Criterion, org.hibernate.criterion.Order)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends DTO> List<T> simpleQuery(Class<T> clazz, Criterion criterion, Order order) {
		List <T> ret = sessionFactory.getCurrentSession().createCriteria(clazz).add(criterion).addOrder(order).list(); 
		logger.info("simple query on " + clazz.getName());
		return ret;
	}

	//	/**
	//	 * @see therapygroup.dao.GenericDao#openSession()
	//	 */
	//	@PostConstruct
	//	public void openSession() {
	//		session = sessionFactory.openSession();	
	//	}	

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#read(java.lang.Class, java.lang.Long)
	 */
	@Override
	public <T extends DTO> T read(Class<T> clazz, Long id) throws DaoException {
		return read(clazz, id, 0);
	}
	
	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#read(java.lang.Class, java.lang.Long, int)
	 */
	@Override
	public <T extends DTO> T read(Class<T> clazz, Long id, int lazyLoadLvels) throws DaoException {
		try {
			if(clazz == null) return null;
			@SuppressWarnings("unchecked")
			T ret = (T) sessionFactory.getCurrentSession().get(clazz, id);
			return Utils.lazyLoad(ret, lazyLoadLvels);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#findByNamedQuery(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends DTO> List<T> findByNamedQuery(final Class<T> clazz, String query, Object ... params) throws DaoException{
		try {
			Query q = sessionFactory.getCurrentSession().createQuery(query);
			if(params != null){
				for(int i = 0; i<params.length; i++){
					q.setParameter(i, params[i]);
				}
			}
			List<T> ret = q.list();
			return ret;
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T extends DTO> List<T> findByNamedQuery(final Class<T> clazz, String query) throws DaoException{
		try {
			return (List<T>) sessionFactory.getCurrentSession().createQuery(query).list();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#findByNamedQueryAndNamedParams(java.lang.Class, java.lang.String, java.util.Map)
	 */
	@Override
	public <T extends Serializable> List<T> findByNamedQueryAndNamedParams(Class<T> clazz, String hql,	Map<String, ? extends Object> params) throws DaoException {
		return this.findByNamedQueryAndNamedParams(clazz, hql, params, null);
	}
	
	@Override
	public <T extends Serializable> List<T> findByNamedQueryAndNamedParams(Class<T> clazz, String hql,	Map<String, ? extends Object> params, Map<String, ? extends Collection<? extends Object>> parameterLists) throws DaoException {
		return findByNamedQueryAndNamedParams(clazz, hql, MAX_RESULTS_PER_LIST, params, parameterLists);
	}
	
	@Override
	public <T extends Serializable> List<T> findByNamedQueryAndNamedParams(Class<T> clazz, String hql, int maxResults,	Map<String, ? extends Object> params, Map<String, ? extends Collection<? extends Object>> parameterLists) throws DaoException {
		try {
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			if(params != null && !params.isEmpty()){
				Iterator<String> keys = params.keySet().iterator();
				while(keys.hasNext()){
					String key = keys.next();
					query.setParameter(key, params.get(key));
				}
			}
			if(parameterLists != null && !parameterLists.isEmpty()){
				Iterator<String> keys = parameterLists.keySet().iterator();
				while(keys.hasNext()){
					String key = keys.next();
					query.setParameterList(key, parameterLists.get(key));
				}
			}
			query.setMaxResults(maxResults);
			@SuppressWarnings("unchecked")
			List<T> ret = query.list();
			return ret;
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	
	@Override
	public int updateQuery(String hql, Map<String, Serializable> params) throws DaoException {
		Query q = sessionFactory.getCurrentSession().createQuery(hql);
		if(params != null && !params.isEmpty()){
			Iterator<String> keys = params.keySet().iterator();
			while(keys.hasNext()){
				String key = keys.next();
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#countByExample(therapygroup.util.DTO)
	 */
	@Override
	public <T extends DTO> long countByExample(T exampleInstance)
			throws DaoException {
		try {
			Criteria c = sessionFactory.getCurrentSession().createCriteria(exampleInstance.getClass());
			Example ex = Example.create(exampleInstance);
			c.setProjection(Projections.rowCount());
			c.add(ex);
			return (Long) c.list().get(0);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}
	

	@Override
	public <T extends DTO> long countByDetachedCriteria(DetachedCriteria detachedCriteria) throws DaoException {
		Criteria c = detachedCriteria.getExecutableCriteria(sessionFactory.getCurrentSession());
		c.setProjection(Projections.rowCount());
		return (Long) c.list().get(0); 
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#countAll(java.lang.Class)
	 */
	@Override
	public <T extends DTO> long countAll(Class<T> clazz) throws DaoException{
		try {
			Criteria crit = sessionFactory.getCurrentSession().createCriteria(clazz);
			crit.setProjection(Projections.rowCount());
			return (Long) crit.list().get(0);
		} catch (HibernateException e) {
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#createCriteria(java.lang.Class)
	 */
	@Override
	@Deprecated
	public <T extends DTO> Criteria createCriteria(Class<T> clazz)
			throws DaoException {
		Criteria c = sessionFactory.getCurrentSession().createCriteria(clazz);
		return c;
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#refresh(therapygroup.util.DTO)
	 */
	@Override
	public void refresh(DTO element) {
		sessionFactory.getCurrentSession().refresh(element);

	}
	
	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#findByDetachedCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public <T extends DTO> List<T> findByDetachedCriteria(DetachedCriteria dc)
			throws DaoException {
		return findByDetachedCriteria(dc, 0);
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#findByDetachedCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public <T extends DTO> List<T> findByDetachedCriteria(DetachedCriteria dc, int lazyLoadLevel)
			throws DaoException {
		return findByDetachedCriteria(dc, lazyLoadLevel, MAX_RESULTS_PER_LIST);
	}
	
	@Override
	public <T extends DTO> List<T> findByDetachedCriteria(DetachedCriteria dc, int lazyLoadLevel, int maxResults)
			throws DaoException {
		Criteria c = dc.getExecutableCriteria(sessionFactory.getCurrentSession());
		c.setMaxResults(maxResults);
		return findByCriteria(c, lazyLoadLevel);
	}
	
	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#findByDetachedCriteria(org.hibernate.criterion.DetachedCriteria)
	 */
	public <T extends DTO> boolean exists(T element) throws DaoException{
		try{
		Query query = sessionFactory.getCurrentSession().createQuery("select 1 from "+ element.getClass().getSimpleName() + " t where t.id = :key");  
		query.setLong("key", element.getId());
		return (query.uniqueResult() != null);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#refresh(java.util.Set)
	 */
	@Override
	public <T extends DTO> void refresh(Set<T> elements) {
		if (elements != null) {
			for (DTO element : elements) {
				this.refresh(element);
			}			
		}		
	}

	/* (non-Javadoc)
	 * @see therapygroup.util.GenericDao#readAllOrderedById(java.lang.Class)
	 */
	@Override	
	@SuppressWarnings("unchecked")
	public <T extends DTO> List<T> readAllOrderedById(Class<T> element)
			throws DaoException {
		try {
			Criteria c = sessionFactory.getCurrentSession().createCriteria(element);
			c.addOrder(Order.asc("id"));	
			c.setMaxResults(50);
			return c.list();
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	@Override
	public void evict(DTO element) throws DaoException {
		sessionFactory.getCurrentSession().evict(element);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DTO> T getById(Class<T> clazz, Long id)
			throws DaoException {
		return (T) sessionFactory.getCurrentSession().get(clazz, id);
	}

}
