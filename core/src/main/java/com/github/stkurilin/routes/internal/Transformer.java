package com.github.stkurilin.routes.internal;

/**
 * @author Stanislav  Kurilin
 */
public interface Transformer<R> {
    R apply(String input);
}
