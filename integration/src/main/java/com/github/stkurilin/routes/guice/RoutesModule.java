package com.github.stkurilin.routes.guice;

import com.github.stkurilin.routes.RuleFromStringFormBuilder;
import com.google.inject.AbstractModule;

import java.io.File;

/**
 * @author Stanislav  Kurilin
 */
public abstract class RoutesModule extends AbstractModule {
    private final HRoutesBuilder routesBuilder = new HRoutesBuilder();

    @Override
    protected final void configure() {
        configureRoutes();
    }

    protected abstract void configureRoutes();

    public RuleFromStringFormBuilder get(String url) {
        return routesBuilder.get(url);
    }

    public RuleFromStringFormBuilder post(String url) {
        return routesBuilder.post(url);
    }

    public RuleFromStringFormBuilder delete(String url) {
        return routesBuilder.delete(url);
    }

    public RuleFromStringFormBuilder put(String url) {
        return routesBuilder.put(url);
    }

    public RoutesServletModule fromFile(String s) {
        return null;
    }

    public RoutesServletModule fromFile(File s) {
        return null;
    }
}
