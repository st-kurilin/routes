package com.github.stkurilin.routes.examples.webguiceservlet;

import com.github.stkurilin.routes.examples.webguiceservlet.services.Foo;
import com.github.stkurilin.routes.examples.webguiceservlet.services.HelloServlet;
import com.github.stkurilin.routes.examples.webguiceservlet.services.ServicesModule;
import com.github.stkurilin.routes.guice.RoutesServletModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

/**
 * @author Stanislav  Kurilin
 */
public class Config extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServicesModule(),
                new ServletModule() {
                    @Override
                    protected void configureServlets() {
                        serve("/foo").with(HelloServlet.class);
                    }
                },
                new RoutesServletModule("/*") {
                    @Override
                    protected void configureRoutes() {
                        get("/bb").to(Foo.class, "noArg");
//                        put("/foo/{name}").to(Foo.class, "oneArg", "name");
//                        delete("/bar/{id}").to(Foo.class, "oneArgWithMapping", "id");
//                        post("/bar/{id}").to(Foo.class, "twoArgs", "id", "'foo'");
                    }
                }
        );
    }
}
