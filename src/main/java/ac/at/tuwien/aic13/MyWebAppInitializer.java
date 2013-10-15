package ac.at.tuwien.aic13;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import ac.at.tuwien.aic13.config.WebAppConfig;
import ac.at.tuwien.aic13.config.ApplicationConfig;

public class MyWebAppInitializer {//implements WebApplicationInitializer {

//    @Override
//    public void onStartup(ServletContext container) {
//      // Create the 'root' Spring application context
//      AnnotationConfigWebApplicationContext rootContext =
//        new AnnotationConfigWebApplicationContext();
//      rootContext.register(PersistenceConfig.class);
//
//      // Manage the lifecycle of the root application context
//      container.addListener(new ContextLoaderListener(rootContext));
//
//      // Create the dispatcher servlet's Spring application context
//      AnnotationConfigWebApplicationContext dispatcherContext =
//        new AnnotationConfigWebApplicationContext();
//      dispatcherContext.register(LifepulseWebConfig.class);
//
//      // Register and map the dispatcher servlet
//      ServletRegistration.Dynamic dispatcher =
//        container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
//      dispatcher.setLoadOnStartup(1);
//      dispatcher.addMapping("/");
//    }

}
