package com.github.stkurilin.routes.inp;

import com.github.stkurilin.routes.conf.Method;

/**
 * @author Stanislav  Kurilin
 */
public interface Request {
    Method method();

    String path();
}
