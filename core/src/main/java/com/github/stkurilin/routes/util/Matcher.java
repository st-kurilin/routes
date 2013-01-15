package com.github.stkurilin.routes.util;

/**
 * @author Stanislav  Kurilin
 */
public interface Matcher<I, T> {
    MatchResult<T> apply(I request);
}
