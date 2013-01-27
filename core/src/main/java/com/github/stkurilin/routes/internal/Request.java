package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.Method;

/**
 * @author Stanislav  Kurilin
 */
public interface Request {
    Method method();

    String path();

    String content();
}
