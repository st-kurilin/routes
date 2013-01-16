package com.github.stkurilin.routes.api;

import com.github.stkurilin.routes.util.MatchResult;

/**
 * @author Stanislav  Kurilin
 */
public interface RequestMatcher {
    MatchResult apply(Request request);
}
