package com.github.stkurilin.routes.impl;

import com.github.stkurilin.routes.api.ArgumentsCollector;
import com.github.stkurilin.routes.api.InstanceFinder;
import com.github.stkurilin.routes.api.Invoker;
import com.github.stkurilin.routes.api.TargetSpec;

import java.util.Map;

import static java.util.Collections.emptyList;

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
        return invoker.apply(targetMethod, emptyList());
    }
}
