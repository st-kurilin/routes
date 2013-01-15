package com.github.stkurilin.routes;

import com.github.stkurilin.routes.conf.ResponseProducer;
import com.github.stkurilin.routes.inp.Request;
import com.github.stkurilin.routes.out.Response;
import com.github.stkurilin.routes.util.MatchResult;
import com.github.stkurilin.routes.util.Matcher;

/**
 * @author Stanislav  Kurilin
 */
public class Routes implements Matcher<Request, Response> {
    private final Invoker invoker;
    private final ArgumentsCollector argumentsCollector;
    private final InstanceMethodRetriever instanceMethodRetriever;
    private final ResponseProducer responseProducer;
    private final RuleMatcher ruleMatcher;

    public Routes(Invoker invoker, InstanceMethodRetriever instanceMethodRetriever, ResponseProducer responseProducer, Iterable<Rule> rules, ArgumentsCollector argumentsCollector) {
        this.invoker = invoker;
        this.instanceMethodRetriever = instanceMethodRetriever;
        this.responseProducer = responseProducer;
        this.argumentsCollector = argumentsCollector;
        this.ruleMatcher = new RuleMatcher(rules);
    }

    @Override
    public MatchResult<Response> apply(final Request request) {
        return ruleMatcher.apply(request).apply(new MatchResult.MatchResultVisitor<Rule.MatchingRule, MatchResult<Response>>() {
            @Override
            public MatchResult<Response> matched(Rule.MatchingRule res) {
                final JavaMethod targetMethod = instanceMethodRetriever.apply(res.targetSpec.clazz(), res.targetSpec.methodId());
                final Object apply = invoker.apply(targetMethod, argumentsCollector.apply(request, res));
                return MatchResult.matched(responseProducer.apply(res, apply));
            }

            @Override
            public MatchResult<Response> skipped() {
                return MatchResult.skipped();
            }
        });
    }
}
