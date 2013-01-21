package com.github.stkurilin.routes.guice;

import com.github.stkurilin.routes.Method;
import com.github.stkurilin.routes.RoutesBuilder;
import com.github.stkurilin.routes.Rule;
import com.github.stkurilin.routes.RuleFromStringFormBuilder;
import com.github.stkurilin.routes.internal.InstanceFinder;
import com.github.stkurilin.routes.internal.RuleCollector;
import com.github.stkurilin.routes.servlet.RoutesFilter;
import com.google.inject.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
class HRoutesBuilder {
    interface AProvider {
        Provider<?> provider(Class<?> clazz);
    }
    final Map<Class, Provider<?>> providers = new HashMap<Class, Provider<?>>();
    private final ArrayList<Rule> rules = new ArrayList<Rule>();

    RuleFromStringFormBuilder rule(Method method, String url) {
        return new RuleFromStringFormBuilder(method, url, new RuleCollector() {
            @Override
            public void addRule(Rule r) {
                rules.add(r);
            }
        });
    }

    public RuleFromStringFormBuilder get(String url) {
        return rule(Method.Get, url);
    }

    public RuleFromStringFormBuilder post(String url) {
        return rule(Method.Post, url);
    }

    public RuleFromStringFormBuilder delete(String url) {
        return rule(Method.Delete, url);
    }

    public RuleFromStringFormBuilder put(String url) {
        return rule(Method.Put, url);
    }

    public void build(AProvider aProvider) {
        for (Rule r : rules) {
            final Class<?> clazz = r.targetSpec().clazz();
            final Provider<?> provider = aProvider.provider(clazz);
            providers.put(clazz, provider);
        }
        RoutesFilter.initRoutes(new RoutesBuilder().instanceFinder(new InstanceFinder() {
            @Override
            public Object apply(Class<?> instanceClass) {
                return providers.get(instanceClass).get();
            }
        }).build());
    }
}
