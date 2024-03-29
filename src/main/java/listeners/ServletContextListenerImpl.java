package listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

	private static final Logger LOGGER = Logger.getLogger(ServletContextListenerImpl.class);
	
	// load CommandContainer class in memory
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		System.out.println("Servlet context initialization");
		ServletContext context = sce.getServletContext();
		initLog4J(context);
		initCommandContainer();
		LOGGER.trace("Servlet context initialization completed");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Servlet context destruction");
	}

	private void initLog4J(ServletContext servletContext) {
		System.out.println("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext.getRealPath("log4j.properties"));
		} catch (Exception ex) {
			LOGGER.error("Cannot configure Log4j", ex);
		}
		LOGGER.debug("Log4j has been initialized");
	}

	private void initCommandContainer() {
		LOGGER.trace("Command container class initialization");
		try {
			Class.forName("command.CommandContainer");
		} catch (ClassNotFoundException ex) {
			LOGGER.debug(ex.getMessage());
		}
		LOGGER.trace("Command container class loaded");
		
	}


}
