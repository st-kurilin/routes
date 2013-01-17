package com.github.stkurilin.routes.api;

/**
 * @author Stanislav  Kurilin
 */
public interface Transformer<R> {
    R apply(String input);
}
