package com.github.stkurilin.routes.examples.webguice;

import com.github.stkurilin.routes.examples.webguice.services.Foo;
import com.github.stkurilin.routes.examples.webguice.services.ServicesModule;
import com.github.stkurilin.routes.guice.RoutesModule;
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
                get("/foo/{name}").to(Foo.class, "oneArg", "name");
                put("/bar/doit").to(Foo.class, "noArg");
                delete("/bar/{id}").to(Foo.class, "oneArgWithMapping", "id");
                post("/bar/{id}").to(Foo.class, "twoArgs", "id", "'foo'");
            }
        });
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
