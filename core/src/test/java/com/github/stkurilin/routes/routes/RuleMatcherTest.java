package com.github.stkurilin.routes.routes;


import com.github.stkurilin.routes.Rule;
import com.github.stkurilin.routes.internal.RuleMatcher;
import com.github.stkurilin.routes.internal.Request;
import com.github.stkurilin.routes.MatchResult;
import org.testng.annotations.Test;

import static com.github.stkurilin.routes.routes.MatchResultUtil.*;
import static com.github.stkurilin.routes.routes.New.iterable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.testng.Assert.assertTrue;

/**
 * @author Stanislav  Kurilin
 */
public class RuleMatcherTest {
    @Test
    public void testSkippingWhenHaventRules() {
        final RuleMatcher matcher = new RuleMatcher(New.<Rule>iterable());
        final MatchResult res = matcher.apply(mock(Request.class));
        assertSkipped(res);
    }

    @Test
    public void testReturnsRuleMatchingResultWhenMatching() {
        final Request request = mock(Request.class);
        final Rule rule = mock(Rule.class);
        final MatchResult<Rule.MatchingRule> matchResult = matched();
        when(rule.apply(request)).thenReturn(matchResult);

        final RuleMatcher matcher = new RuleMatcher(iterable(rule));
        final MatchResult res = matcher.apply(request);

        assertTrue(res == matchResult);
    }

    @Test
    public void testPreferringMatchResult() {
        final Request request = mock(Request.class);

        final Rule matchableRule = matchableRule(request);
        final Rule skippableRule1 = skippableRule(request);
        final Rule skippableRule2 = skippableRule(request);

        final RuleMatcher matcher = new RuleMatcher(iterable(skippableRule1, matchableRule, skippableRule2));
        final MatchResult<Rule.MatchingRule> res = matcher.apply(request);

        assertMatched(res);
    }

    private static Rule skippableRule(Request request) {
        final Rule result = mock(Rule.class);
        when(result.apply(request)).thenReturn(skipped());
        return result;
    }

    private static Rule matchableRule(Request request) {
        final Rule result = mock(Rule.class);
        when(result.apply(request)).thenReturn(matched());
        return result;
    }


}
