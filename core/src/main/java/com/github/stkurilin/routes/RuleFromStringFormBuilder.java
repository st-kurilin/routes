package com.github.stkurilin.routes;

import com.github.stkurilin.routes.internal.RuleCollector;
import com.github.stkurilin.routes.internal.RuleCreator;
import com.github.stkurilin.routes.internal.UriSpecFromString;

import java.util.Arrays;

/**
 * @author Stanislav  Kurilin
 */
public class RuleFromStringFormBuilder<T> {
    private final Method method;
    private final String path;
    private final RuleCollector collector;
    private final T builder;

    public RuleFromStringFormBuilder(Method method, String path, RuleCollector collector, T builder) {
        this.method = method;
        this.path = path;
        this.collector = collector;
        this.builder = builder;
    }

    public T to(final Class<?> clazz, final String methodId, final String... args) {
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
        }));
        return builder;
    }
}
