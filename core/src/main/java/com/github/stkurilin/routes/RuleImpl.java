package com.github.stkurilin.routes;

import com.github.stkurilin.routes.conf.Method;
import com.github.stkurilin.routes.conf.TargetSpec;
import com.github.stkurilin.routes.conf.UriSpec;
import com.github.stkurilin.routes.inp.Request;
import com.github.stkurilin.routes.util.MatchResult;

import java.util.HashMap;

/**
 * @author Stanislav  Kurilin
 */
public class RuleImpl implements Rule {
    private final Method method;
    private final UriSpec uriSpec;
    private final TargetSpec targetSpec;

    public RuleImpl(Method method, UriSpec uriSpec, TargetSpec targetSpec) {
        this.method = method;
        this.uriSpec = uriSpec;
        this.targetSpec = targetSpec;
    }

    @Override
    public MatchResult<MatchingRule> apply(Request input) {
        return MatchResult.matched(new MatchingRule(targetSpec, new HashMap<String, String>() {{
            put("foo", "fooval");
        }}));
    }
}
