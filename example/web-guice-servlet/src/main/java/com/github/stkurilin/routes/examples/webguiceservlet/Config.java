package com.github.stkurilin.routes.examples.webguiceservlet;

import com.github.stkurilin.routes.guice.RoutesServletModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.github.stkurilin.routes.examples.webguiceservlet.services.Foo;
import com.github.stkurilin.routes.examples.webguiceservlet.services.ServicesModule;

/**
 * @author Stanislav  Kurilin
 */
public class Config extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServicesModule(),
                new RoutesServletModule("/services/*") {
                    @Override
                    protected void configureRoutes() {
                        get("/foo/{name}").to(Foo.class, "oneArg", "name");
                        put("/bar/doit").to(Foo.class, "noArg");
                        delete("/bar/{id}").to(Foo.class, "oneArgWithMapping", "id");
                        post("/bar/{id}").to(Foo.class, "twoArgs", "id", "'foo'");
                    }
                });
    }
}
