package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.TargetSpec;

import java.util.Map;

/**
 * @author Stanislav Kurilin
 */
public class Caller {
    private final Invoker invoker;
    private final InstanceFinder instanceFinder;
    private final ArgumentsCollector argumentsCollector;


    public Caller(Invoker invoker, InstanceFinder instanceFinder, ArgumentsCollector argumentsCollector) {
        this.invoker = invoker;
        this.instanceFinder = instanceFinder;
        this.argumentsCollector = argumentsCollector;
    }

    public Object apply(TargetSpec targetSpec, Map<String, String> availableInputs) {
        final JavaMethod targetMethod = JavaMethod.from(instanceFinder.apply(targetSpec.clazz()), targetSpec.methodId());
        return invoker.apply(targetMethod, argumentsCollector.apply(targetSpec, availableInputs));
    }
}