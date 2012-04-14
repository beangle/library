package org.beangle.web.spring;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.util.ClassUtils;
import org.springframework.util.ObjectUtils;

public class ContextLoader implements ServletContextListener {

	public static final String ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE = "WebApplicationContext.ROOT";

	public static final String APPLICATION_CONTEXT_ID_PREFIX = "WebApplicationContext:";

	public static final String CONTEXT_CLASS_PARAM = "contextClass";

	public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";

	public static final String LAZY_INIT_PROCESSORS = "LazyInitProcessors";

	public void contextInitialized(ServletContextEvent sce) {
		initApplicationContext(sce.getServletContext());
	}

	public void contextDestroyed(ServletContextEvent sce) {
		closeApplicationContext(sce.getServletContext());
	}

	protected ConfigurableApplicationContext createApplicationContext(ServletContext sc) {
		Class<?> contextClass = determineContextClass(sc);
		if (!ConfigurableApplicationContext.class.isAssignableFrom(contextClass)) { throw new ApplicationContextException(
				"Custom context class [" + contextClass.getName() + "] is not of type ["
						+ ConfigurableApplicationContext.class.getName() + "]"); }
		ConfigurableApplicationContext wac = (ConfigurableApplicationContext) BeanUtils
				.instantiateClass(contextClass);
		return wac;
	}

	public ApplicationContext initApplicationContext(ServletContext servletContext) {
		if (null != getContext(servletContext)) { throw new IllegalStateException(
				"Cannot initialize context because there is already a root application context present - "
						+ "check whether you have multiple ContextListener* definitions in your web.xml!"); }
		Logger logger = LoggerFactory.getLogger(ContextLoader.class);
		servletContext.log("Initializing Spring root ApplicationContext");
		if (logger.isInfoEnabled()) {
			logger.info("Root ApplicationContext: initialization started");
		}
		long startTime = System.currentTimeMillis();
		try {
			ConfigurableApplicationContext context = createApplicationContext(servletContext);
			configureAndRefreshApplicationContext((ConfigurableApplicationContext) context, servletContext);

			servletContext.setAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, context);

			logger.debug("Published root ApplicationContext as ServletContext attribute with name ["
					+ ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE + "]");
			long elapsedTime = System.currentTimeMillis() - startTime;
			logger.info("Root ApplicationContext: initialization completed in " + elapsedTime + " ms");

			// lazy init processor
			List<LazyInitProcessor> processors = getOrCreateLazyInitProcessors(servletContext);
			for (LazyInitProcessor processor : processors)
				processor.init(context);
			servletContext.removeAttribute(LAZY_INIT_PROCESSORS);

			return context;
		} catch (RuntimeException ex) {
			logger.error("Context initialization failed", ex);
			throw ex;
		} catch (Error err) {
			logger.error("Context initialization failed", err);
			throw err;
		}
	}

	protected void configureAndRefreshApplicationContext(ConfigurableApplicationContext wac, ServletContext sc) {
		wac.setId(APPLICATION_CONTEXT_ID_PREFIX + ObjectUtils.getDisplayString(sc.getServletContextName()));
		String initParameter = sc.getInitParameter(CONFIG_LOCATION_PARAM);
		if (initParameter != null) {
			if (wac instanceof AbstractRefreshableConfigApplicationContext) {
				((AbstractRefreshableConfigApplicationContext) wac).setConfigLocation(initParameter);
			}
		}
		customizeContext(sc, wac);
		wac.refresh();
	}

	protected void customizeContext(ServletContext servletContext,
			ConfigurableApplicationContext applicationContext) {
	}

	protected Class<?> determineContextClass(ServletContext servletContext) {
		String contextClassName = servletContext.getInitParameter(CONTEXT_CLASS_PARAM);
		if (contextClassName != null) {
			try {
				return ClassUtils.forName(contextClassName, ClassUtils.getDefaultClassLoader());
			} catch (ClassNotFoundException ex) {
				throw new ApplicationContextException("Failed to load custom context class ["
						+ contextClassName + "]", ex);
			}
		} else {
			return XmlWebApplicationContext.class;
		}
	}

	public void closeApplicationContext(ServletContext servletContext) {
		servletContext.log("Closing Spring root ApplicationContext");
		ConfigurableApplicationContext context = getContext(servletContext);
		if (null != context) context.close();
		servletContext.removeAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
	}

	public static ConfigurableApplicationContext getContext(ServletContext servletContext) {
		Object context = servletContext.getAttribute(ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		if (null == context) {
			context = servletContext
					.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
		}
		if (!(context instanceof Exception)) return (ConfigurableApplicationContext) context;
		return null;
	}

	public static List<LazyInitProcessor> getOrCreateLazyInitProcessors(ServletContext servletContext) {
		@SuppressWarnings("unchecked")
		List<LazyInitProcessor> processors = (List<LazyInitProcessor>) servletContext
				.getAttribute(LAZY_INIT_PROCESSORS);
		if (null == processors) {
			processors = new ArrayList<LazyInitProcessor>();
			servletContext.setAttribute(LAZY_INIT_PROCESSORS, processors);
		}
		return processors;
	}

}