package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.Rule;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public class InputsCollector {
    public Map<String, Object> apply(Request request, Rule.MatchingRule appliedRule) {
        final Map<String, Object> res = new HashMap<String, Object>();
        final Map retrieved = (Map) appliedRule.getRetrieved();
        res.putAll(retrieved);
        res.put("body", request.content());
        return res;
    }
}
