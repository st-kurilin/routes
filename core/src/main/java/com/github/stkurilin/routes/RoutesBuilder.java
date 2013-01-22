package com.github.stkurilin.routes;

import com.github.stkurilin.routes.internal.*;

import java.util.ArrayList;
import java.util.Collections;

public class RoutesBuilder {
    private Invoker invoker = new InvokerWithMapping(Collections.<Class<?>, Transformer<?>>emptyMap());
    private InstanceFinder instanceFinder;
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

    public RoutesBuilder instanceFinder(InstanceFinder instanceFinder) {
        this.instanceFinder = instanceFinder;
        return this;
    }

    public RoutesBuilder responseProducer(ResponseProducer responseProducer) {
        this.responseProducer = responseProducer;
        return this;
    }

    public RoutesBuilder setRules(ArrayList<Rule> rules) {
        this.rules = rules;
        return this;
    }

    public RoutesBuilder addRule(Rule rule) {
        rules.add(rule);
        return this;
    }


    public Routes build() {
        return new Routes(new RuleMatcher(rules), new InputsCollector(), new Caller(invoker, instanceFinder, new ArgumentsCollector()), responseProducer);
    }

}