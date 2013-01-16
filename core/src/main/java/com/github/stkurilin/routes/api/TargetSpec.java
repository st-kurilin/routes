package com.github.stkurilin.routes.api;

/**
 * @author Stanislav  Kurilin
 */
public interface TargetSpec {
    Class<?> clazz();
    String methodId();
    Iterable<String> args();
}
