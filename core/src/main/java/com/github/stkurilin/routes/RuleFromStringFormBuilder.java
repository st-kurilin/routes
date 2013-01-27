package com.github.stkurilin.routes;

import com.github.stkurilin.routes.internal.RuleCollector;
import com.github.stkurilin.routes.internal.UriSpecFromString;

import java.util.Arrays;

/**
 * @author Stanislav  Kurilin
 */
public class RuleFromStringFormBuilder {
    private final Method method;
    private final String path;
    private final RuleCollector collector;

    public RuleFromStringFormBuilder(Method method, String path, RuleCollector collector) {
        this.method = method;
        this.path = path;
        this.collector = collector;
    }

    public void to(final Class<?> clazz, final String methodId, final String... args) {
        collector.addRule(new RuleCreator().apply(method, new UriSpecFromString().apply(path), new TargetSpec() {
            @Override
            public Class<? extends Object> clazz() {
                return clazz;
            }

            @Override
            public String methodId() {
                return methodId;
            }

            @Override
            public Iterable<String> args() {
                return Arrays.asList(args);
            }
        }, "")); //todo
    }
}
