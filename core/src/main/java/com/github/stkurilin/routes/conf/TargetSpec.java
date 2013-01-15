package com.github.stkurilin.routes.conf;

/**
 * @author Stanislav  Kurilin
 */
public interface TargetSpec {
    Class<?> clazz();
    String methodId();
    Iterable<String> args();
}
