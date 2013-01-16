package com.github.stkurilin.routes.api;

/**
 * @author Stanislav  Kurilin
 */
public interface InstanceFinder {
    Object apply(Class<?> instanceClass);
}
