package com.github.stkurilin.routes.routes;


import com.github.stkurilin.routes.Rule;
import com.github.stkurilin.routes.RuleImpl;
import com.github.stkurilin.routes.conf.Method;
import com.github.stkurilin.routes.conf.TargetSpec;
import com.github.stkurilin.routes.conf.UriSpec;
import com.github.stkurilin.routes.inp.Request;
import com.github.stkurilin.routes.util.MatchResult;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.Test;

import static com.github.stkurilin.routes.routes.MatchResultUtil.assertMatched;
import static com.github.stkurilin.routes.routes.MatchResultUtil.assertSkipped;
import static com.google.common.collect.ImmutableList.of;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertTrue;

/**
 * @author Stanislav Kurilin
 */
public class RuleTest {
    @Test
    public void testDontMatchOnUri() throws Exception {
        final RuleImpl rule = rule(Method.Get, of(literal("foo")));
        final Request request = request(Method.Get, "/foobar");
        assertSkipped(rule.apply(request));
    }

    @Test
    public void testDontMatchOnMethod() throws Exception {
        final RuleImpl rule = rule(Method.Get, of(literal("foo")));
        final Request request = request(Method.Post, "/foobar");
        assertSkipped(rule.apply(request));
    }

    @Test(dependsOnMethods = {"testDontMatchOnUri", "testDontMatchOnMethod"})
    public void testMatchingWithoutParams() throws Exception {
        final RuleImpl rule = rule(Method.Get, of(literal("foo")));
        final Request request = request(Method.Get, "/foo");
        assertMatched(rule.apply(request));
    }

    @Test(dependsOnMethods = "testMatchingWithoutParams")
    public void testRetrieveSimpleVal() throws Exception {
        final RuleImpl rule = rule(Method.Get, of(literal("foo"), matcher("id")));
        final Request request = request(Method.Get, "/foo/2");

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
        final RuleImpl rule = rule(Method.Get, of(literal("foo"), matcher("parent"), literal("bar"), matcher("child")));
        final Request request = request(Method.Get, "/foo/22/bar/e");

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
        final RuleImpl rule = rule(Method.Get, of(literal("foo"), matcher("id")));
        final Request request = request(Method.Get, "/foo/ho/bo");

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

    private static RuleImpl rule(Method method, Iterable<UriSpec.Item> items) {
        final UriSpec uriSpec = mock(UriSpec.class);
        when(uriSpec.path()).thenReturn(items);
        return new RuleImpl(method, uriSpec, mock(TargetSpec.class));
    }

    private Request request(Method method, String path) {
        final Request request = mock(Request.class);
        when(request.method()).thenReturn(method);
        when(request.path()).thenReturn(path);
        return request;
    }

    private static UriSpec.Item literal(final String value) {
        return new UriSpec.Item() {
            @Override
            public <R> R apply(UriSpec.ItemVisitor<R> visitor) {
                return visitor.literal(value);
            }
        };
    }

    private static UriSpec.Item matcher(final String name) {
        return new UriSpec.Item() {
            @Override
            public <R> R apply(UriSpec.ItemVisitor<R> visitor) {
                return visitor.matcher(name);
            }
        };
    }
}
