package com.github.stkurilin.routes.internal;

/**
 * @author Stanislav  Kurilin
 */
public interface InstanceFinder {
    Object apply(Class<?> instanceClass);
}
