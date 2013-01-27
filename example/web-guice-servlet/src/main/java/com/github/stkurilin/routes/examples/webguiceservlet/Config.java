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
                        post("/foo/{name}").to(Foo.class, "oneArg", "name");
                        load("./foo.routes");
//                        put("/bar/{id}").to(Foo.class, "twoArgs", "id", "'foo'");
//                        delete("/bar/{id}").to(Foo.class, "oneArgWithMapping", "id");
                    }
                }
        );
    }
}
