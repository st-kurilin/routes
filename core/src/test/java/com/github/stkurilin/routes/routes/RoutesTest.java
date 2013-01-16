package com.github.stkurilin.routes.routes;

import com.github.stkurilin.routes.api.*;
import com.github.stkurilin.routes.api.TargetSpec;
import com.github.stkurilin.routes.impl.Caller;
import com.github.stkurilin.routes.impl.RuleMatcher;
import com.github.stkurilin.routes.util.MatchResult;
import org.testng.annotations.Test;

import java.util.Map;

import static com.github.stkurilin.routes.routes.MatchResultUtil.assertSkipped;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

/**
 * @author Stanislav  Kurilin
 */
public class RoutesTest {
    @Test
    public void testSkippIfCouldNotApplyRule() throws Exception {
        final Request request = mock(Request.class);
        final RuleMatcher ruleMatcher = mock(RuleMatcher.class);
        when(ruleMatcher.apply(request)).thenReturn(MatchResult.<Rule.MatchingRule>skipped());
        final Routes routes = new Routes(ruleMatcher, mock(InputsCollector.class), mock(Caller.class), mock(ResponseProducer.class));

        final MatchResult<Response> res = routes.apply(request);

        assertSkipped(res);
    }

    @Test
    public void testProcessMatchedRule() throws Exception {
        final Request request = mock(Request.class);
        final Rule.MatchingRule matchingRule = mock(Rule.MatchingRule.class);
        final TargetSpec targetSpec = mock(TargetSpec.class);
        when(matchingRule.getTargetSpec()).thenReturn(targetSpec);
        final RuleMatcher ruleMatcher = mock(RuleMatcher.class);
        when(ruleMatcher.apply(request)).thenReturn(MatchResult.matched(matchingRule));
        final InputsCollector inputsCollector = mock(InputsCollector.class);
        final Map map = mock(Map.class);
        when(inputsCollector.apply(request, matchingRule)).thenReturn(map);
        final Object targetRes = mock(Object.class);
        final Caller caller = mock(Caller.class);
        when(caller.apply(targetSpec, map)).thenReturn(targetRes);
        final ResponseProducer responseProducer = mock(ResponseProducer.class);
        final Response response = mock(Response.class);
        when(responseProducer.apply(matchingRule, targetRes)).thenReturn(response);
        final Routes routes = new Routes(ruleMatcher, inputsCollector, caller, responseProducer);
        final MatchResult<Response> responseResult = routes.apply(request);
        assertTrue(responseResult.apply(new MatchResult.MatchResultVisitor<Response, Boolean>() {
            @Override
            public Boolean matched(Response res) {
                return res.equals(response);
            }

            @Override
            public Boolean skipped() {
                return false;
            }
        }));
    }
}
