package com.github.stkurilin.routes.routes;

import com.github.stkurilin.routes.internal.Invoker;
import com.github.stkurilin.routes.internal.InvokerWithMapping;
import com.github.stkurilin.routes.internal.JavaMethod;
import com.github.stkurilin.routes.internal.Retriever;
import com.google.common.collect.ImmutableList;
import org.testng.annotations.Test;

import java.util.HashSet;

import static org.mockito.Mockito.*;

/**
 * @author Stanislav  Kurilin
 */
public class InvokerTest {
    @Test
    public void testNoArgsInvocation() throws Exception {
        final Invoker invoker = invoker();
        final JavaMethod method = mock(JavaMethod.class);
        when(method.argClasses()).thenReturn(ImmutableList.<Class<?>>of());

        invoker.apply(method, ImmutableList.<Object>of());

        verify(method).apply(ImmutableList.<String>of());
    }

    @Test
    public void testStringsInvocation() throws Exception {
        final Invoker invoker = invoker();
        final JavaMethod method = mock(JavaMethod.class);
        when(method.argClasses()).thenReturn(ImmutableList.<Class<?>>of(String.class));

        invoker.apply(method, ImmutableList.<Object>of("abc"));

        verify(method).apply(ImmutableList.<String>of("abc"));
    }

    @Test
    public void testInvocationWithMapping() throws Exception {
        final Invoker invoker = invoker(new Retriever() {
            @Override
            public Object apply(Object input, Class<?> target) {
                if (target.getClass().equals(Foo.class) && input instanceof String)
                    return new Foo(input.toString());
                return null;
            }
        });
        final JavaMethod method = mock(JavaMethod.class);
        when(method.argClasses()).thenReturn(ImmutableList.<Class<?>>of(Integer.class));

        invoker.apply(method, ImmutableList.<Object>of("123"));

        verify(method).apply(ImmutableList.<Integer>of(123));
    }

    private InvokerWithMapping invoker(final Retriever... retrievers) {
        return new InvokerWithMapping(new HashSet<Retriever>() {{
            for (Retriever each : retrievers) add(each);
            add(Retriever.DEFAULT);
        }});
    }

    static class Foo {
        final String value;

        Foo(String value) {
            this.value = value;
        }
    }
}
