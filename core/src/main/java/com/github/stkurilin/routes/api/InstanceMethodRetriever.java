package com.github.stkurilin.routes.api;

/**
 * @author Stanislav  Kurilin
 */
public interface InstanceMethodRetriever {
    JavaMethod apply(Class<?> clazz, String methodId);
}
