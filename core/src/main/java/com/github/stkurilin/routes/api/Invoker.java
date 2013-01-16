package com.github.stkurilin.routes.api;


/**
 * @author Stanislav  Kurilin
 */
public interface Invoker {
    Object apply(JavaMethod method, Iterable<Object> args);
}
