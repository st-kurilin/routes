package com.github.stkurilin.routes;

import java.util.List;

/**
 * @author Stanislav  Kurilin
 */
public interface JavaMethod {
    List<Class<?>> argClasses();

    Object apply(Iterable<Object> args);
}
