package com.github.stkurilin.routes.api;

import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public class InputsCollector {
    public Map<String, String> apply(Request request, Rule.MatchingRule appliedRule) {
        return appliedRule.getRetrieved();
    }
}
