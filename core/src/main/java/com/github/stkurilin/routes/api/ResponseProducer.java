package com.github.stkurilin.routes.api;

/**
 * @author Stanislav  Kurilin
 */
public interface ResponseProducer {
    Response apply(Rule.MatchingRule appliedRule, Object result);
}
