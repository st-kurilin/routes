package com.github.stkurilin.routes.api;

/**
 * @author Stanislav  Kurilin
 */
public interface Request {
    Method method();

    String path();
}
