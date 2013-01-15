package com.github.stkurilin.routes;

import com.github.stkurilin.routes.conf.ResponseProducer;
import com.github.stkurilin.routes.inp.Request;
import com.github.stkurilin.routes.out.Response;
import com.github.stkurilin.routes.util.MatchResult;
import com.github.stkurilin.routes.util.Matcher;

import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public class Routes implements Matcher<Request, Response> {
    private final RuleMatcher ruleMatcher;
    private final Caller caller;
    private final ResponseProducer responseProducer;

    public Routes(RuleMatcher ruleMatcher, Caller caller, ResponseProducer responseProducer) {
        this.ruleMatcher = ruleMatcher;
        this.caller = caller;
        this.responseProducer = responseProducer;
    }

    @Override
    public MatchResult<Response> apply(final Request request) {
        return ruleMatcher
                .apply(request)
                .apply(new MatchResult.MatchResultVisitor<Rule.MatchingRule, MatchResult<Response>>() {
                    @Override
                    public MatchResult<Response> matched(Rule.MatchingRule appliedRule) {
                        return MatchResult.matched(responseProducer.apply(
                                appliedRule,
                                caller.apply(appliedRule.targetSpec,
                                        collectInputs(request, appliedRule))));
                    }

                    @Override
                    public MatchResult<Response> skipped() {
                        return MatchResult.skipped();
                    }
                });
    }

    private Map<String, String> collectInputs(Request request, Rule.MatchingRule appliedRule) {
        return appliedRule.retrieved;
    }
}
