package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.Method;

import java.io.InputStream;

/**
 * @author Stanislav  Kurilin
 */
public interface Request {
    Method method();

    String path();

    InputStream content();
}
