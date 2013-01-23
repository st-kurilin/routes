package com.github.stkurilin.routes.internal;

/**
 * @author Stanislav  Kurilin
 */
public interface Transformer<F, T> {
    T apply(F input);
}
