package com.github.stkurilin.routes;

import com.github.stkurilin.routes.conf.ResponseProducer;
import com.github.stkurilin.routes.inp.Request;
import com.github.stkurilin.routes.out.Response;

import java.lang.reflect.Method;
import java.util.*;

public class RoutesBuilder {
    private Invoker invoker = new Invoker() {
        @Override
        public Object apply(JavaMethod method, Iterable<Object> args) {
            return method.apply(args);
        }
    };
    private InstanceMethodRetriever instanceMethodRetriever = new InstanceMethodRetriever() {
        final Map<Class<?>, Object> instances = new HashMap<Class<?>, Object>();

        @Override
        public JavaMethod apply(final Class<?> clazz, String methodId) {
            for (final Method each : clazz.getMethods()) {
                if (each.getName().equals(methodId))
                    return new JavaMethod() {
                        @Override
                        public List<Class<?>> argClasses() {
                            return Arrays.asList(each.getParameterTypes());
                        }

                        @Override
                        public Object apply(Iterable<Object> args) {
                            try {
                                if (!instances.containsKey(clazz))
                                    instances.put(clazz, clazz.newInstance());
                                return instances.get(clazz);
                            } catch (InstantiationException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    };
            }
            throw new RuntimeException(String.format("Couldn't find method %s in %s", methodId, clazz));
        }
    };
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
            return (Iterable) appliedRule.retrieved.values();
        }
    };

    public RoutesBuilder setInvoker(Invoker invoker) {
        this.invoker = invoker;
        return this;
    }

    public RoutesBuilder setInstanceMethodRetriever(InstanceMethodRetriever instanceMethodRetriever) {
        this.instanceMethodRetriever = instanceMethodRetriever;
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
                return appliedRule.retrieved;
            }
        }, new Caller(invoker, argumentsCollector, instanceMethodRetriever), responseProducer);
    }
}