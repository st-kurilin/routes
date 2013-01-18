package com.github.stkurilin.routes.internal;

import com.github.stkurilin.routes.MatchResult;
import com.github.stkurilin.routes.Matcher;
import com.github.stkurilin.routes.Rule;

/**
 * @author Stanislav  Kurilin
 */
public class RuleMatcher implements Matcher<Request, Rule.MatchingRule> {
    private final Iterable<Rule> iterable;

    public RuleMatcher(Iterable<Rule> iterable) {
        this.iterable = iterable;
    }

    @Override
    public MatchResult<Rule.MatchingRule> apply(Request request) {
        for (Rule rule : iterable) {
            final MatchResult<Rule.MatchingRule> matchResult = rule.apply(request);
            if (matchResult.apply(MatchResult.matchedPredicate())) {
                return matchResult;
            }
        }
        return MatchResult.skipped();
    }
}
