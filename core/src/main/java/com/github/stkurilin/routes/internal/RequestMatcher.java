package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.MatchResult;

/**
 * @author Stanislav  Kurilin
 */
public interface RequestMatcher {
    MatchResult apply(Request request);
}
