package org.repeid.services.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.repeid.models.RepeidSessionFactory;

public class RepeidSessionDestroyListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        RepeidSessionFactory sessionFactory = (RepeidSessionFactory) sce.getServletContext().getAttribute(RepeidSessionFactory.class.getName());
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

}
