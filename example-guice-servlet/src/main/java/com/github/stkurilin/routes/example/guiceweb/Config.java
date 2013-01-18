package com.github.stkurilin.routes.example.guiceweb;

import com.github.stkurilin.routes.RouteGuavaWebModuleBuilder;
import com.github.stkurilin.routes.example.guiceweb.services.Foo;
import com.github.stkurilin.routes.example.guiceweb.services.ServicesModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * @author Stanislav  Kurilin
 */
public class Config extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new ServicesModule(),
                new RouteGuavaWebModuleBuilder("/services/*")
                        .fromFile("")
                        .get("/foo/{name}").to(Foo.class, "oneArg", "name")
                        .put("/bar/doit").to(Foo.class, "noArg")
                        .delete("/bar/{id}").to(Foo.class, "oneArgWithMapping", "$id")
                        .post("/bar/{id}").to(Foo.class, "twoArgs", "$id", "id")
        );
    }
}
