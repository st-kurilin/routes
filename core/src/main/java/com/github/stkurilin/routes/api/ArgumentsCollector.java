package com.github.stkurilin.routes.api;

/**
 * @author Stanislav  Kurilin
 */
public interface ArgumentsCollector {
    Iterable<Object> apply(Request request, Rule.MatchingRule appliedRule);
}
