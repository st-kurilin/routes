package com.github.stkurilin.routes.examples.webresetguicejackson;

import com.github.stkurilin.routes.examples.webresetguicejackson.services.ServicesModule;
import com.github.stkurilin.routes.guice.RoutesModule;
import com.github.stkurilin.routes.retrievers.JacksonRetriever;
import com.github.stkurilin.routes.serializers.JacksonSerializer;
import com.google.inject.Guice;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Stanislav  Kurilin
 */
public class Config implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Guice.createInjector(new ServicesModule(), new RoutesModule() {
            @Override
            protected void configureRoutes() {
                deserializer(new JacksonRetriever());
                serializer(new JacksonSerializer());
                load("./PersonService.routes");
            }
        }
        );
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
