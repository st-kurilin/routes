package com.github.stkurilin.routes;

import com.github.stkurilin.routes.inp.Request;
import com.github.stkurilin.routes.util.MatchResult;

/**
 * @author Stanislav  Kurilin
 */
public interface RequestMatcher {
    MatchResult apply(Request request);
}
