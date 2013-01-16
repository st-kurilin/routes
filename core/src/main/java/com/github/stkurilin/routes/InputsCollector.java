package com.github.stkurilin.routes;

import com.github.stkurilin.routes.inp.Request;

import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public interface InputsCollector {
    Map<String, String> apply(Request request, Rule.MatchingRule appliedRule);
}
