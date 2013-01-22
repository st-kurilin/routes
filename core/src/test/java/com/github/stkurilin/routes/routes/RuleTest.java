package com.github.stkurilin.routes.routes;


import com.github.stkurilin.routes.MatchResult;
import com.github.stkurilin.routes.Method;
import com.github.stkurilin.routes.Rule;
import com.github.stkurilin.routes.internal.Request;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.Test;

import static com.github.stkurilin.routes.routes.MatchResultUtil.assertMatched;
import static com.github.stkurilin.routes.routes.MatchResultUtil.assertSkipped;
import static com.github.stkurilin.routes.routes.TestHelper.*;
import static com.google.common.collect.ImmutableList.of;
import static org.testng.Assert.assertTrue;

/**
 * @author Stanislav Kurilin
 */
public class RuleTest {
    @Test
    public void testDontMatchOnUri() throws Exception {
        final Rule rule = rule(Method.GET, of(literal("foo")));
        final Request request = request(Method.GET, "/foobar");
        assertSkipped(rule.apply(request));
    }

    @Test
    public void testDontMatchOnMethod() throws Exception {
        final Rule rule = rule(Method.GET, of(literal("foo")));
        final Request request = request(Method.POST, "/foobar");
        assertSkipped(rule.apply(request));
    }

    @Test(dependsOnMethods = {"testDontMatchOnUri", "testDontMatchOnMethod"})
    public void testMatchingWithoutParams() throws Exception {
        final Rule rule = rule(Method.GET, of(literal("foo")));
        final Request request = request(Method.GET, "/foo");
        assertMatched(rule.apply(request));
    }

    @Test(dependsOnMethods = "testMatchingWithoutParams")
    public void testRetrieveSimpleVal() throws Exception {
        final Rule rule = rule(Method.GET, of(literal("foo"), matcher("id")));
        final Request request = request(Method.GET, "/foo/2");

        assertTrue(rule.apply(request).apply(new MatchResult.MatchResultVisitor<Rule.MatchingRule, Boolean>() {
            @Override
            public Boolean matched(Rule.MatchingRule res) {
                return res.getRetrieved().get("id").equals("2");
            }

            @Override
            public Boolean skipped() {
                return false;
            }
        }));
    }

    @Test(dependsOnMethods = "testRetrieveSimpleVal")
    public void testRetrieveSeveralVal() {
        final Rule rule = rule(Method.GET, of(literal("foo"), matcher("parent"), literal("bar"), matcher("child")));
        final Request request = request(Method.GET, "/foo/22/bar/e");

        assertTrue(rule.apply(request).apply(new MatchResult.MatchResultVisitor<Rule.MatchingRule, Boolean>() {
            @Override
            public Boolean matched(Rule.MatchingRule res) {
                return ImmutableMap.of("parent", "22", "child", "e").equals(res.getRetrieved());
            }

            @Override
            public Boolean skipped() {
                return false;
            }
        }));
    }

    @Test(dependsOnMethods = "testRetrieveSimpleVal")
    public void testRetrieveValWithSlashed() {
        final Rule rule = rule(Method.GET, of(literal("foo"), matcher("id")));
        final Request request = request(Method.GET, "/foo/ho/bo");

        assertTrue(rule.apply(request).apply(new MatchResult.MatchResultVisitor<Rule.MatchingRule, Boolean>() {
            @Override
            public Boolean matched(Rule.MatchingRule res) {
                return ImmutableMap.of("id", "ho/bo").equals(res.getRetrieved());
            }

            @Override
            public Boolean skipped() {
                return false;
            }
        }));
    }


}
