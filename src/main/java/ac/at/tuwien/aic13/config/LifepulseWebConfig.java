package ac.at.tuwien.aic13.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import ac.at.tuwien.aic13.services.MainService;
import ac.at.tuwien.aic13.services.impl.MainServiceImpl;


@Configuration
@EnableWebMvc
@ComponentScan(value= {"ac.at.tuwien.aic13.AIC13G3P3.AIC13G3P3.controllers"})
@EnableTransactionManagement(proxyTargetClass=true, mode=AdviceMode.PROXY)
public class LifepulseWebConfig extends WebMvcConfigurerAdapter{ 
	@Autowired
	ApplicationContext context;
	
	
	@Bean
	ViewResolver viewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		//resolver.setPrefix("");
		resolver.setSuffix(".jsp");
		return resolver;
	}
	
	@Bean
	MainService mainService(){
		return new MainServiceImpl();
	}

}
