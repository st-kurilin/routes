package com.github.stkurilin.routes.api;


import com.github.stkurilin.routes.impl.JavaMethod;

/**
 * @author Stanislav  Kurilin
 */
public interface Invoker {
    Object apply(JavaMethod method, Iterable<String> args);
}
