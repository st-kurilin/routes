package com.github.stkurilin.routes.routes;

import com.github.stkurilin.routes.internal.Invoker;
import com.github.stkurilin.routes.internal.InvokerWithMapping;
import com.github.stkurilin.routes.internal.Transformer;
import com.github.stkurilin.routes.internal.JavaMethod;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

/**
 * @author Stanislav  Kurilin
 */
public class InvokerTest {
    @Test
    public void testNoArgsInvocation() throws Exception {
        final Invoker invoker = new InvokerWithMapping(ImmutableMap.<Class<?>, Transformer<?>>of());
        final JavaMethod method = mock(JavaMethod.class);
        when(method.argClasses()).thenReturn(ImmutableList.<Class<?>>of());

        invoker.apply(method, ImmutableList.<String>of());

        verify(method).apply(ImmutableList.<String>of());
    }

    @Test
    public void testStringsInvocation() throws Exception {
        final Invoker invoker = new InvokerWithMapping(ImmutableMap.<Class<?>, Transformer<?>>of());
        final JavaMethod method = mock(JavaMethod.class);
        when(method.argClasses()).thenReturn(ImmutableList.<Class<?>>of(String.class));

        invoker.apply(method, ImmutableList.<String>of("abc"));

        verify(method).apply(ImmutableList.<String>of("abc"));
    }

    @Test
    public void testInvocationWithMapping() throws Exception {
        final Invoker invoker = new InvokerWithMapping(ImmutableMap.<Class<?>, Transformer<?>>of(Integer.class, new Transformer<Integer>() {
            @Override
            public Integer apply(String input) {
                return Integer.valueOf(input);
            }
        }));
        final JavaMethod method = mock(JavaMethod.class);
        when(method.argClasses()).thenReturn(ImmutableList.<Class<?>>of(Integer.class));

        invoker.apply(method, ImmutableList.<String>of("123"));

        verify(method).apply(ImmutableList.<Integer>of(123));
    }
}
