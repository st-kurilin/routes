package com.github.stkurilin.routes;

import com.github.stkurilin.routes.internal.*;

import java.util.*;

public class RoutesBuilder {
    private Invoker invoker;
    private InstanceFinder instanceFinder = new InstanceFinder() {
        final Map<Class<?>, Object> instancies = new HashMap<Class<?>, Object>();

        @Override
        public Object apply(Class<?> instanceClass) {
            if (!instancies.containsKey(instanceClass))
                try {
                    instancies.put(instanceClass, instanceClass.newInstance());
                } catch (InstantiationException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            return instancies.get(instanceClass);
        }
    };
    private ArrayList<Rule> rules = new ArrayList<Rule>();
    private ResponseProducer responseProducer = new ResponseProducer() {
        @Override
        public Response apply(Rule.MatchingRule appliedRule, final Object result) {
            return new Response() {
                @Override
                public String toBody() {
                    return result.toString();
                }
            };
        }
    };
    private Set<Retriever> retrievers = new HashSet<Retriever>();

    public RoutesBuilder instanceFinder(InstanceFinder instanceFinder) {
        this.instanceFinder = instanceFinder;
        return this;
    }

    public RoutesBuilder responseProducer(ResponseProducer responseProducer) {
        this.responseProducer = responseProducer;
        return this;
    }

    public RoutesBuilder setRules(Iterable<Rule> rules) {
        for (Rule rule : rules) addRule(rule);
        return this;
    }

    public RoutesBuilder addRule(Rule rule) {
        rules.add(rule);
        return this;
    }


    public Routes build() {
        invoker = new InvokerWithMapping(new HashSet<Retriever>() {{
            addAll(retrievers);
            add(Retriever.DEFAULT);
        }});
        return new Routes(new RuleMatcher(rules), new InputsCollector(), new Caller(invoker, instanceFinder, new ArgumentsCollector()), responseProducer);
    }


    public RoutesBuilder setRetrievers(Set<Retriever> retrievers) {
        this.retrievers = retrievers;
        return this;
    }
}