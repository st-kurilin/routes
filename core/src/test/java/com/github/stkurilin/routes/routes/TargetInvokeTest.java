package com.github.stkurilin.routes.routes;

import com.github.stkurilin.routes.*;
import com.github.stkurilin.routes.internal.InstanceFinder;
import org.testng.annotations.Test;

import java.util.Collections;

import static com.github.stkurilin.routes.routes.TestHelper.*;
import static com.google.common.collect.ImmutableList.of;
import static org.mockito.Mockito.*;

/**
 * @author Stanislav Kurilin
 */
public class TargetInvokeTest {
    public static class Foo {
        public String foo1() {
            return "foo1";
        }

        public String foo2(String arg) {
            return "foo2";
        }
    }

    @Test
    public void testInvokeWithoutParams() throws Exception {
        final InstanceFinder instanceFinder = mock(InstanceFinder.class);
        final Foo mock = mock(Foo.class);
        when(instanceFinder.apply(Foo.class)).thenReturn(mock);

        final TargetSpec targetSpec = mock(TargetSpec.class);
        when(targetSpec.clazz()).thenReturn((Class) Foo.class);
        when(targetSpec.methodId()).thenReturn("foo1");
        when(targetSpec.args()).thenReturn(Collections.<String>emptyList());

        final Routes routes = new RoutesBuilder().instanceFinder(instanceFinder)
                .addRule(rule(of(literal("bar1")), targetSpec))
                .build();

        routes.apply(request(Method.GET, "/bar1"));

        verify(mock).foo1();
    }

    @Test
    public void testInvokeWithStringParam() throws Exception {
        final InstanceFinder instanceFinder = mock(InstanceFinder.class);
        final Foo mock = mock(Foo.class);
        when(instanceFinder.apply(Foo.class)).thenReturn(mock);

        final TargetSpec targetSpec = mock(TargetSpec.class);
        when(targetSpec.clazz()).thenReturn((Class) Foo.class);
        when(targetSpec.methodId()).thenReturn("foo2");
        when(targetSpec.args()).thenReturn(of("id"));

        final Routes routes = new RoutesBuilder().instanceFinder(instanceFinder)
                .addRule(rule(of(literal("bar2"), matcher("id")), targetSpec))
                .build();

        routes.apply(request(Method.GET, "/bar2/2"));

        verify(mock).foo2("2");
    }

    static Rule rule(Iterable<UriSpec.Item> items, TargetSpec mock) {
        final UriSpec uriSpec = mock(UriSpec.class);
        when(uriSpec.path()).thenReturn(items);

        return new RuleCreator().apply(Method.GET, uriSpec, mock, "");
    }

}
