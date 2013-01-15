package com.github.stkurilin.routes;

/**
 * @author Stanislav  Kurilin
 */
public interface InstanceMethodRetriever {
    JavaMethod apply(Class<?> clazz, String methodId);
}
