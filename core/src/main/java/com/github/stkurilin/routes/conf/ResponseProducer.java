package com.github.stkurilin.routes.conf;

import com.github.stkurilin.routes.Rule;
import com.github.stkurilin.routes.out.Response;

/**
 * @author Stanislav  Kurilin
 */
public interface ResponseProducer {
    Response apply(Rule.MatchingRule appliedRule, Object result);
}
