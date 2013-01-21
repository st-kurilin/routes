package com.github.stkurilin.routes.guice;

import com.github.stkurilin.routes.RuleFromStringFormBuilder;
import com.github.stkurilin.routes.servlet.RoutesFilter;
import com.google.inject.Provider;
import com.google.inject.servlet.ServletModule;

import java.io.File;

/**
 * @author Stanislav  Kurilin
 */
public abstract class RoutesServletModule extends ServletModule {
    private final String path;
    private final HRoutesBuilder routesBuilder = new HRoutesBuilder();

    public RoutesServletModule(String path) {
        this.path = path;
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

    @Override
    protected final void configureServlets() {
        configureRoutes();
        final RoutesFilter filter = new RoutesFilter();
        routesBuilder.build(new HRoutesBuilder.AProvider() {
            @Override
            public Provider<?> provider(Class<?> clazz) {
                return getProvider(clazz);
            }
        });
        filter(path).through(filter);
    }


    public RoutesServletModule fromFile(String s) {
        return this;
    }

    public RoutesServletModule fromFile(File s) {
        return this;
    }
}
