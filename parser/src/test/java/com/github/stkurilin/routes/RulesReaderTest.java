package com.github.stkurilin.routes;

import org.mockito.ArgumentMatcher;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.google.common.collect.ImmutableList.of;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Stanislav Kurilin
 */
public class RulesReaderTest {
    private RulesReader rulesReader;
    private RuleCreator ruleCreator;

    @BeforeMethod
    public void setUp() {
        ruleCreator = mock(RuleCreator.class);
        rulesReader = new RulesReader(ruleCreator);
    }

    @Test
    public void testSimplestCase() {
        read("GET /foo com.github.stkurilin.routes.Foo#foo");
        check(Method.Get, uriSpec(literal("foo")), Foo.class, "foo", new ArrayList<String>());
    }

    @Test
    public void testSeveralRules() {
        read("GET /foo com.github.stkurilin.routes.Foo#foo");
        read("GET /bar com.github.stkurilin.routes.Foo#bar");
        check(Method.Get, uriSpec(literal("foo")), Foo.class, "foo", new ArrayList<String>());
        check(Method.Get, uriSpec(literal("bar")), Foo.class, "bar", new ArrayList<String>());
    }

    @Test
    public void testImport() {
        read("import com.github.stkurilin.routes.Foo");
        read("GET /foo Foo#foo");
        check(Method.Get, uriSpec(literal("foo")), Foo.class, "foo", new ArrayList<String>());
    }

    @Test
    public void testMatcher() {
        read("GET /foo/{id} com.github.stkurilin.routes.Foo#foo");
        check(Method.Get, uriSpec(literal("foo"), matcher("id")), Foo.class, "foo", new ArrayList<String>());
    }

    @Test
    public void testEmptyArgsDeclaration() throws Exception {
        read("GET /foo/ com.github.stkurilin.routes.Foo#foo()");
        check(Method.Get, uriSpec(literal("foo")), Foo.class, "foo", new ArrayList<String>());
    }

    private void check(Method method, UriSpecMatcher uriSpec, Class<?> clazz, String methodId, List<String> args) {
        verify(ruleCreator).apply(eq(method), argThat(uriSpec), eq(clazz), eq(methodId), eq(args));
    }

    private UriSpecMatcher uriSpec(UriSpec.Item... items) {
        return new UriSpecMatcher(Arrays.asList(items));
    }

    private void read(String inp) {
        rulesReader.apply(new StringReader(inp));
    }

    static UriSpec.Item literal(final String value) {
        return new UriSpec.Item() {
            @Override
            public <R> R apply(UriSpec.ItemVisitor<R> visitor) {
                return visitor.literal(value);
            }
        };
    }

    static UriSpec.Item matcher(final String name) {
        return new UriSpec.Item() {
            @Override
            public <R> R apply(UriSpec.ItemVisitor<R> visitor) {
                return visitor.matcher(name);
            }
        };
    }

    private static class UriSpecMatcher extends ArgumentMatcher<UriSpec> {
        private final Iterable<UriSpec.Item> items;

        private UriSpecMatcher(Iterable<UriSpec.Item> items) {
            this.items = items;
        }

        @Override
        public boolean matches(Object argument) {
            if (!(argument instanceof UriSpec)) return false;
            final Iterator<UriSpec.Item> rightIt = ((UriSpec) argument).path().iterator();
            for (UriSpec.Item left : items) {
                if (!rightIt.hasNext()) return false;
                final UriSpec.Item right = rightIt.next();
                if (!left.apply(new UriSpec.ItemVisitor<Boolean>() {
                    @Override
                    public Boolean literal(final String leftValue) {
                        return right.apply(new UriSpec.ItemVisitor<Boolean>(false) {
                            @Override
                            public Boolean literal(String value) {
                                return value.equals(leftValue);
                            }
                        });
                    }

                    @Override
                    public Boolean matcher(final String leftName) {
                        return right.apply(new UriSpec.ItemVisitor<Boolean>(false) {
                            @Override
                            public Boolean matcher(String name) {
                                return leftName.equals(name);
                            }
                        });
                    }
                })) return false;
            }
            return !rightIt.hasNext();
        }
    }
}
