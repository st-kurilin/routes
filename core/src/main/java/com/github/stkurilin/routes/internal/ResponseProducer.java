package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.Rule;

/**
 * @author Stanislav  Kurilin
 */
public interface ResponseProducer {
    Response apply(Rule.MatchingRule appliedRule, Object result);
}
