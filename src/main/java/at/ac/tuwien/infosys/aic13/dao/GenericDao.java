package at.ac.tuwien.infosys.aic13.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

public interface GenericDao {
	/**
	 * Creates a new DTO of the given type and returns it.
	 *
	 * @param <T> the generic type
	 * @param element the element
	 * @return the new object
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> void create(T element) throws DaoException;
	
	/**
	 * Read a specific DTO with the given ID.
	 * If no element is found null will be returned.
	 *
	 * @param <T> The generic type
	 * @param element the element (if null, then null will be returned)
	 * @return the t
	 * @see therapygroup.util.GenericDao#read(Class)
	 */
	public <T extends DTO> T read(T element);
	
	/**
	 * Read a specific DTO with the given ID.
	 * The additional ID parameter will be taken. The ID of the element will be ignored.<br />
	 *
	 * @param <T> the generic type
	 * @param element the element (if null, then null will be returned)
	 * @param id the id (if null, then null will be returned)
	 * @return the t, or null if not found.
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> T read(T element, Long id) throws DaoException;
	
	/**
	 * Read a specific DTO with the given ID.
	 * The additional ID parameter will be taken. The ID of the element will be ignored.<br />
	 *
	 * @param <T> the generic type
	 * @param element the element (if null, then null will be returned)
	 * @param id the id (if null, then null will be returned)
	 * @param lazyLoadLvels - how many levels to lazy-load
	 * @return the t, or null if not found.
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO>  T read(Class<T> clazz, Long id, int lazyLoadLvels) throws DaoException;
	
	/**
	 * Read a specific DTO with the given ID.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param id (if null, then null will be returned)
	 * @return the t
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> T read(Class<T> clazz, Long id) throws DaoException;
	
	/**
	 * Read all elements of the given type. (Can be limited)
	 *
	 * @param <T> The generic type
	 * @param element the element
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> List<T> readAll(Class<T> element)throws DaoException;
	
	
	/**
	 * Read list with limit.
	 *
	 * @param <T> the generic type
	 * @param element the element
	 * @param maxResults the max results
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> List<T> readLimit(Class<T> element, int maxResults)throws DaoException;
	
	/**
	 * Read list with limit.
	 *
	 * @param <T> the generic type
	 * @param element the element
	 * @param maxResults the max results
	 * @param lazyLoadLevels - how many levels to lazy-load
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> List<T> readLimit(Class<T> element, int maxResults, int lazyLoadLevels)throws DaoException;
	
	/**
	 * Update the given DTO.
	 *
	 * @param <T> The generic type
	 * @param element The element to update.
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> void update(T element) throws DaoException;
	
	/**
	 * Delete the given DTO.
	 *
	 * @param <T> the generic type
	 * @param element the element
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> void delete(T element) throws DaoException;	
	
	/**
	 * Query.
	 *
	 * @param <T> the generic type
	 * @param element the element - just for the type not as example
	 * @param criterion the criterion
	 * @param order the order
	 * @return the matched query list
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> List<T> simpleQuery(T element, Criterion criterion, Order order) throws DaoException;

	/**
	 * Query.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param criterion the criterion
	 * @param order the order
	 * @return the matched query list
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> List<T> simpleQuery(Class<T> clazz, Criterion criterion, Order order) throws DaoException;
	
	/**
	 * Find using a named query.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param queryName the name of the query
	 * @param params the query parameters
	 * @return the list of entities
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> List<T> findByNamedQuery(final Class<T> clazz, final String queryName, Object... params) throws DaoException;
	
	/**
	 * Find using a named query.
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @param hql the name of the query
	 * @param params the query parameters
	 * @return the list of entities
	 * @throws DaoException the dao exception
	 */
	public <T extends Serializable> List<T> findByNamedQueryAndNamedParams(final Class<T> clazz, final String hql, final Map<String, ?extends Object> params) throws DaoException;

	/**
	 * 
	 * @param clazz
	 * @param hql
	 * @param params
	 * @param parameterLists - parameter Lists.
	 * @return
	 * @throws DaoException
	 */
	public <T extends Serializable> List<T> findByNamedQueryAndNamedParams(Class<T> clazz, String hql,
			Map<String, ? extends Object> params,
			Map<String, ? extends Collection<? extends Object>> parameterLists)
			throws DaoException;
	
	/**
	 * 
	 * @param clazz
	 * @param hql
	 * @param maxResults
	 * @param params
	 * @param parameterLists
	 * @return
	 * @throws DaoException
	 */
	public <T extends Serializable> List<T> findByNamedQueryAndNamedParams(Class<T> clazz, String hql, int maxResults,
			Map<String, ? extends Object> params,
			Map<String, ? extends Collection<? extends Object>> parameterLists)
			throws DaoException;
	
	/**
	 * Count entities based on an example.
	 *
	 * @param <T> the generic type
	 * @param exampleInstance the search criteria
	 * @return the number of entities
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> long countByExample(final T exampleInstance) throws DaoException;
	
	public <T extends DTO> long countByDetachedCriteria(DetachedCriteria criteria) throws DaoException;
	
    /**
     * Count all entities.
     *
     * @param <T> the generic type
     * @param clazz the clazz
     * @return the number of entities
     * @throws DaoException the dao exception
     */
	public <T extends DTO> long countAll(final Class<T> clazz) throws DaoException;
	
	
	/**
	 * This method finds all objects which match the given example-entity.<br />
	 * All empty or zero fields are ignored in the query.<br />
	 * Nested elements are also ignored. (e.g. Search for all Ueberweisungen with a given Patient does not work.)
	 *
	 * @param <T> the generic type
	 * @param example the example
	 * @return never null, but an empty list.
	 * @throws DaoException if something goes wrong
	 */
	public <T extends DTO> List<T> findByExample(final T example) throws DaoException;

	/**
	 * Like therapygroup.util.GenericDao#findByExample(DTO) but you can provide multiple examples.
	 *
	 * @param <T> the generic type
	 * @param examples the examples
	 * @return the list
	 * @throws DaoException the dao exception
	 * @see therapygroup.util.GenericDao#findByExample(DTO)
	 */
	public <T extends DTO> List<T> findByExamples(@SuppressWarnings("unchecked") T ... examples) throws DaoException;

	/**
	 * Creates the criteria.
	 * This method is deprecated, use e.g. DetachedCriteria.forClass(<YOURCLASS>.class).
	 *
	 * @param <T> the generic type
	 * @param clazz the clazz
	 * @return the criteria
	 * @throws DaoException the dao exception
	 * @see GenericDao#findByDetachedCriteria(DetachedCriteria)
	 */
	@Deprecated
	public <T extends DTO> Criteria createCriteria(Class<T> clazz) throws DaoException;
	
	/**
	 * Refreshes the given DTO.
	 *
	 * @param element the element
	 */
	public <T extends DTO> void refresh(T element);
	
	/**
	 * Find by detached criteria.
	 *
	 * @param <T> the generic type
	 * @param dc the dc
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> List<T> findByDetachedCriteria(DetachedCriteria dc) throws DaoException;
	
	/**
	 * Find by detached criteria.
	 *
	 * @param <T> the generic type
	 * @param dc the dc
	 * @param lazyLoadLevels - how many levels to lazy-load
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> List<T> findByDetachedCriteria(DetachedCriteria dc, int lazyLoadLevels) throws DaoException;

	/**
	 * Find by detached criteria.
	 *
	 * @param <T> the generic type
	 * @param dc the dc
	 * @param lazyLoadLevels - how many levels to lazy-load
	 * @param maxREsults - How many results to return max.
	 * @return the list
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> List<T> findByDetachedCriteria(DetachedCriteria dc, int lazyLoadLevels, int maxResults) throws DaoException;

	/**
	 * Check if element already exists in DB.
	 *
	 * @param <T> the generic type
	 * @return boolean
	 * @throws DaoException the dao exception
	 */
	public <T extends DTO> boolean exists(T element) throws DaoException;

	/**
	 * Refreshes the given Set of DTOs.
	 *
	 * @param element the element
	 */
	public <T extends DTO> void refresh(Set<T> elements);

	/**
	 * Returns all DTOs of the given type, ordered by id.
	 *
	 * @param element the element
	 */
	public <T extends DTO> List<T> readAllOrderedById(Class<T> element) throws DaoException;
	
	/**
	 * Evicts (removes) the given element from the DB-Session.
	 * @param element
	 * @throws DaoException
	 */
	public void evict(DTO element) throws DaoException;
	
	/**
	 * This method is to execute a HQL UPDATE command with named Params.
	 * @param hql
	 * @param namedParams
	 * @return
	 * @throws DaoException
	 */
	public int updateQuery(String hql, Map<String, Serializable> namedParams) throws DaoException;
	
	public <T extends DTO>  T getById(Class<T> clazz, Long id) throws DaoException;

	public <T extends DTO> List<T> findByNamedQuery(Class<T> clazz, String query) throws DaoException;
}
