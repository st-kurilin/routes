package com.github.stkurilin.routes;

import com.github.stkurilin.routes.conf.TargetSpec;

import java.util.Map;

import static java.util.Collections.emptyList;

/**
 * @author Stanislav Kurilin
 */
public class Caller {
    private final Invoker invoker;
    private final ArgumentsCollector argumentsCollector;
    private final InstanceMethodRetriever instanceMethodRetriever;

    public Caller(Invoker invoker, ArgumentsCollector argumentsCollector, InstanceMethodRetriever instanceMethodRetriever) {
        this.invoker = invoker;
        this.argumentsCollector = argumentsCollector;
        this.instanceMethodRetriever = instanceMethodRetriever;
    }

    public Object apply(TargetSpec targetSpec, Map<String, String> availableInputs) {
        final JavaMethod targetMethod = instanceMethodRetriever.apply(targetSpec.clazz(), targetSpec.methodId());
        final Object res = invoker.apply(targetMethod, emptyList());
        return res;
    }
}
