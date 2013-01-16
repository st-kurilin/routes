package com.github.stkurilin.routes.impl;

import com.github.stkurilin.routes.api.*;

import java.util.ArrayList;
import java.util.Map;

public class RoutesBuilder {
    private Invoker invoker = new Invoker() {
        @Override
        public Object apply(JavaMethod method, Iterable<Object> args) {
            return method.apply(args);
        }
    };
    private InstanceFinder instanceFinder;

    public RoutesBuilder setInstanceFinder(InstanceFinder instanceFinder) {
        this.instanceFinder = instanceFinder;
        return this;
    }

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
    private ArrayList<Rule> rules = new ArrayList<Rule>();
    private ArgumentsCollector argumentsCollector = new ArgumentsCollector() {
        @Override
        public Iterable<Object> apply(Request request, Rule.MatchingRule appliedRule) {
            return (Iterable) appliedRule.getRetrieved().values();
        }
    };

    public RoutesBuilder setInvoker(Invoker invoker) {
        this.invoker = invoker;
        return this;
    }

    public RoutesBuilder setResponseProducer(ResponseProducer responseProducer) {
        this.responseProducer = responseProducer;
        return this;
    }

    public RoutesBuilder setRules(Iterable<Rule> rules) {
        this.rules = new ArrayList<Rule>();
        for (Rule each : rules) this.rules.add(each);
        return this;
    }

    public RoutesBuilder setArgumentsCollector(ArgumentsCollector argumentsCollector) {
        this.argumentsCollector = argumentsCollector;
        return this;
    }

    public Routes createRoutes() {
        return new Routes(new RuleMatcher(rules), new InputsCollector() {
            @Override
            public Map<String, String> apply(Request request, Rule.MatchingRule appliedRule) {
                return appliedRule.getRetrieved();
            }
        }, new Caller(invoker, instanceFinder, argumentsCollector), responseProducer);
    }
}