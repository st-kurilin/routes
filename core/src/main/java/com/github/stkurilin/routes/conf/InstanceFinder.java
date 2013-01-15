package com.github.stkurilin.routes.conf;

/**
 * @author Stanislav  Kurilin
 */
public interface InstanceFinder {
    Object apply(Class<?> instanceClass);
}
