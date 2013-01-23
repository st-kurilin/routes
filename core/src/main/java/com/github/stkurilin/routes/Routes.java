package com.github.stkurilin.routes;

import com.github.stkurilin.routes.internal.*;
import com.github.stkurilin.routes.internal.Caller;
import com.github.stkurilin.routes.internal.RuleMatcher;

/**
 * @author Stanislav  Kurilin
 */
public class Routes implements Matcher<Request, Response> {
    private final RuleMatcher ruleMatcher;
    private final InputsCollector inputsCollector;
    private final Caller caller;
    private final ResponseProducer responseProducer;

    public Routes(RuleMatcher ruleMatcher, InputsCollector inputsCollector, Caller caller, ResponseProducer responseProducer) {
        this.ruleMatcher = ruleMatcher;
        this.inputsCollector = inputsCollector;
        this.caller = caller;
        this.responseProducer = responseProducer;
    }

    @Override
    public MatchResult<Response> apply(final Request input) {
        return ruleMatcher
                .apply(input)
                .apply(new MatchResult.MatchResultVisitor<Rule.MatchingRule, MatchResult<Response>>() {
                    @Override
                    public MatchResult<Response> matched(Rule.MatchingRule appliedRule) {
                        return MatchResult.matched(responseProducer.apply(
                                appliedRule,
                                caller.apply(appliedRule.getTargetSpec(),
                                        inputsCollector.apply(input, appliedRule))));
                    }

                    @Override
                    public MatchResult<Response> skipped() {
                        return MatchResult.skipped();
                    }
                });
    }
}
