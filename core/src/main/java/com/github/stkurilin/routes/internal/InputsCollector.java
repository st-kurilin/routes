package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.Rule;

import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public class InputsCollector {
    public Map<String, String> apply(Request request, Rule.MatchingRule appliedRule) {
        return appliedRule.getRetrieved();
    }
}
