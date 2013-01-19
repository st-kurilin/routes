package com.github.stkurilin.routes;

import com.github.stkurilin.routes.internal.RuleCreator;
import org.mockito.ArgumentMatcher;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

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
        verify(ruleCreator).apply(eq(Method.Get), uriSpec(literal("/foo")), eq(Foo.class), eq("foo"), eq(new ArrayList<String>()));
    }

    private UriSpec uriSpec(UriSpec.Item... items) {
        return argThat(new UriSpecMatcher(Arrays.asList(items)));
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
