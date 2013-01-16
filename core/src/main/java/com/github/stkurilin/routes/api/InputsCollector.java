package com.github.stkurilin.routes.api;

import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public interface InputsCollector {
    Map<String, String> apply(Request request, Rule.MatchingRule appliedRule);
}
