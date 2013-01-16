package com.github.stkurilin.routes.api;

import java.util.List;

/**
 * @author Stanislav  Kurilin
 */
public interface JavaMethod {
    List<Class<?>> argClasses();

    Object apply(Iterable<Object> args);
}
