package com.github.stkurilin.routes.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import static com.github.stkurilin.routes.internal.Precond.checkState;

/**
 * @author Stanislav  Kurilin
 */
public class InvokerWithMapping implements Invoker {
    private final Set<Retriever> retrievers;


    public InvokerWithMapping(Set<Retriever> retrievers) {
        this.retrievers = retrievers;
    }

    @Override
    public Object apply(JavaMethod method, Iterable<Object> argsPassed) {
        final ArrayList<Object> args = new ArrayList<Object>();
        final Iterator<Class<?>> requered = method.argClasses().iterator();
        for (Object s : argsPassed) {
            checkState(requered.hasNext(), "Passed more args than params presented");
            final Class<?> requeredClass = requered.next();

            args.add(map(s, requeredClass));


        }
        return method.apply(args);
    }

    private Object map(Object s, Class<?> requeredClass) {
        for (Retriever r : retrievers) {
            final Object retrieved = r.apply(s, requeredClass);
            if (retrieved != null) {
                return retrieved;
            }
        }
        throw new RuntimeException(String.format("Could not retrieve transformer for: %s", requeredClass));
    }
}
