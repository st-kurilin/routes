package com.github.stkurilin.routes;

import com.github.stkurilin.routes.internal.InstanceFinder;
import com.github.stkurilin.routes.internal.RuleCollector;
import com.google.inject.Provider;
import com.google.inject.servlet.ServletModule;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public class RouteGuavaWebModuleBuilder extends ServletModule {
    final Map<Class, Provider<?>> providers = new HashMap<Class, Provider<?>>();
    private final String path;
    private final ArrayList<Rule> rules = new ArrayList<Rule>();

    public RouteGuavaWebModuleBuilder(String path) {
        this.path = path;
    }

    public RuleFromStringFormBuilder<RouteGuavaWebModuleBuilder> get(String url) {
        return rule(Method.Get, url);
    }

    public RuleFromStringFormBuilder<RouteGuavaWebModuleBuilder> post(String url) {
        return rule(Method.Post, url);
    }

    public RuleFromStringFormBuilder<RouteGuavaWebModuleBuilder> delete(String url) {
        return rule(Method.Delete, url);
    }

    public RuleFromStringFormBuilder<RouteGuavaWebModuleBuilder> put(String url) {
        return rule(Method.Put, url);
    }

    private RuleFromStringFormBuilder<RouteGuavaWebModuleBuilder> rule(Method method, String url) {
        return new RuleFromStringFormBuilder<RouteGuavaWebModuleBuilder>(method, url, new RuleCollector() {
            @Override
            public void addRule(Rule r) {
                rules.add(r);
            }
        }, this);
    }

    @Override
    protected final void configureServlets() {
        final RoutesFilter filter = new RoutesFilter();
        for (Rule r : rules) {
            final Class<?> clazz = r.targetSpec().clazz();
            final Provider<?> provider = getProvider(clazz);
            providers.put(clazz, provider);
        }
        filter.initRoutes(new RoutesBuilder().instanceFinder(new InstanceFinder() {
            @Override
            public Object apply(Class<?> instanceClass) {
                return providers.get(instanceClass).get();
            }
        }).build());
        filter(path).through(filter);
    }

    public RouteGuavaWebModuleBuilder fromFile(String s) {
        return this;
    }

    public RouteGuavaWebModuleBuilder fromFile(File s) {
        return this;
    }
}
