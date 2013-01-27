package com.github.stkurilin.routes;

/**
 * @author Stanislav  Kurilin
 */
public interface Matcher<I, T> {
    MatchResult<T> apply(I input);
}
