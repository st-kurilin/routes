package com.github.stkurilin.routes.examples;

import com.github.stkurilin.routes.Routes;
import com.github.stkurilin.routes.RoutesBuilder;
import com.github.stkurilin.routes.servlet.RoutesFilter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Stanislav  Kurilin
 */
public class Config implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        RoutesFilter.initRoutes(new RoutesBuilder().build());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
