package com.github.stkurilin.routes.api;

import com.github.stkurilin.routes.impl.JavaMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static com.github.stkurilin.routes.impl.Precond.checkState;

/**
 * @author Stanislav  Kurilin
 */
public class InvokerWithMapping implements Invoker {
    private final Map<Class<?>, Transformer<?>> transformers;

    public InvokerWithMapping(Map<Class<?>, Transformer<?>> transformers) {
        this.transformers = new HashMap<Class<?>, Transformer<?>>();
        this.transformers.put(String.class, new Transformer<String>() {
            @Override
            public String apply(String input) {
                return input;
            }
        });
        this.transformers.putAll(transformers);
    }

    @Override
    public Object apply(JavaMethod method, Iterable<String> argsInString) {
        final ArrayList<Object> args = new ArrayList<Object>();
        final Iterator<Class<?>> requered = method.argClasses().iterator();
        for (String s : argsInString) {
            checkState(requered.hasNext(), "Passed more args than params presented");
            final Class<?> requeredClass = requered.next();
            checkState(transformers.containsKey(requeredClass), String.format("Could not find transformer for: %s", requeredClass));
            args.add(transformers.get(requeredClass).apply(s));
        }
        return method.apply(args);
    }
}
