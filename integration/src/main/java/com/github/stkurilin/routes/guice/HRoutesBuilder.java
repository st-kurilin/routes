package com.github.stkurilin.routes.guice;

import com.github.stkurilin.routes.*;
import com.github.stkurilin.routes.internal.InstanceFinder;
import com.github.stkurilin.routes.internal.RuleCollector;
import com.github.stkurilin.routes.servlet.RoutesFilter;
import com.google.inject.Provider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
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
    private final RulesReader rulesReader = new RulesReader();

    RuleFromStringFormBuilder rule(Method method, String url) {
        return new RuleFromStringFormBuilder(method, url, new RuleCollector() {
            @Override
            public void addRule(Rule r) {
                rules.add(r);
            }
        });
    }

    public RuleFromStringFormBuilder get(String url) {
        return rule(Method.GET, url);
    }

    public RuleFromStringFormBuilder post(String url) {
        return rule(Method.POST, url);
    }

    public RuleFromStringFormBuilder delete(String url) {
        return rule(Method.DELETE, url);
    }

    public RuleFromStringFormBuilder put(String url) {
        return rule(Method.PUT, url);
    }

    public void fromFile(File file) {
        try {
            for (Rule each : rulesReader.apply(new FileReader(file))) rules.add(each);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void from(InputStreamReader reader) {
        for (Rule each : rulesReader.apply(reader)) rules.add(each);
    }

    public void build(AProvider aProvider) {
        for (Rule r : rules) {
            final Class<?> clazz = r.targetSpec().clazz();
            final Provider<?> provider = aProvider.provider(clazz);
            providers.put(clazz, provider);
        }
        RoutesFilter.initRoutes(new RoutesBuilder().setRules(rules).instanceFinder(new InstanceFinder() {
            @Override
            public Object apply(Class<?> instanceClass) {
                return providers.get(instanceClass).get();
            }
        }).build());
    }
}
