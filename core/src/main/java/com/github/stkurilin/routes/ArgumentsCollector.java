package com.github.stkurilin.routes;

import com.github.stkurilin.routes.inp.Request;

/**
 * @author Stanislav  Kurilin
 */
public interface ArgumentsCollector {
    Iterable<Object> apply(Request request, Rule.MatchingRule appliedRule);
}
