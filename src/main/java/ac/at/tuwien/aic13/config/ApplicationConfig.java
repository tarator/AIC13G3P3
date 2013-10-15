package ac.at.tuwien.aic13.config;

import java.io.Serializable;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import ac.at.tuwien.aic13.dto.LogEntry;
import ac.at.tuwien.aic13.dto.Melder;
import ac.at.tuwien.aic13.persistence.GenericDao;
import ac.at.tuwien.aic13.persistence.HibernateDaoImpl;

@Configuration
public class ApplicationConfig {
	
	Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
	
	/** The Application Context. */
	@Autowired
	ApplicationContext context;
	
	/**
	 * Gets the database url.
	 *
	 * @return the database url
	 */
	@Bean(name="databaseUrl")
	public String getDatabaseUrl(){
		return "jdbc:mysql://localhost/AIC13G3P3";
	}
		
	/**
	 * Gets the session factory properties.
	 *
	 * @return the session factory properties
	 */
	@Bean(name="sessionFactoryProperties")
	public Properties getSessionFactoryProperties(){
		
		
		Properties props = new Properties();
		props.put("hibernate.dialect", MySQL5InnoDBDialect.class.getName());
//		String config = context.getEnvironment().getProperty("hibernate.hbm2ddl.auto", "update");
		props.put("hibernate.hbm2ddl.auto", "create-drop");
//		config = context.getEnvironment().getProperty("hibernate.hbm2ddl.show_sql", "false");
		props.put("hibernate.show_sql", "true");
		props.put("hibernate.format_sql", "true");
		return props;
	}
	
	
	/** The Constant ANNOTATED_CLASSES. */
	@SuppressWarnings("unchecked")
	private static final Class<? extends Serializable>[] ANNOTATED_CLASSES=new Class[]{
		Melder.class,
		LogEntry.class
	};
	
	
	/**
	 * Gets the annotated classes.
	 *
	 * @return the annotated classes
	 */
	private static Class<? extends Serializable>[] getAnnotatedClasses(){
		return ANNOTATED_CLASSES;
	}
	
	/**
	 * Gets the data source.
	 * This bean represents the application's MYSQL datasource, without using xml.
	 *
	 * @return the data source
	 */
	@Bean(name="dataSource")
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(getDatabaseUrl());
		dataSource.setUsername("AIC13G3P3");
		dataSource.setPassword("AIC13G3P3");
		return dataSource;
	}

	/**
	 * Session factory.
	 * This bean represents the Hibernate Session Factory. By declaring this bean
	 * it can easily be injected into Spring DAOs later on.
	 * 
	 * @return the local session factory bean
	 */
	@Bean(name="sessionFactory")
	public LocalSessionFactoryBean sessionFactory() {
		
		LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
		factory.setAnnotatedClasses(getAnnotatedClasses());		
		factory.setHibernateProperties(getSessionFactoryProperties());
		factory.setDataSource(dataSource());
		return factory;
	}

	@Bean(name="genericDao")
	public GenericDao genericDao() {
		return new HibernateDaoImpl();
	}

	@Bean(name="txManager")
	PlatformTransactionManager txManager(){
		HibernateTransactionManager htm = new HibernateTransactionManager(sessionFactory().getObject());
		return htm;
	}


}

