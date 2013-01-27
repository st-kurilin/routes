package com.github.stkurilin.routes.internal;


/**
 * @author Stanislav  Kurilin
 */
public interface Invoker {
    Object apply(JavaMethod method, Iterable<Object> args);
}
