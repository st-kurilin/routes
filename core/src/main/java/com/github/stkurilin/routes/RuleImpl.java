package com.github.stkurilin.routes;

import com.github.stkurilin.routes.conf.Method;
import com.github.stkurilin.routes.conf.TargetSpec;
import com.github.stkurilin.routes.conf.UriSpec;
import com.github.stkurilin.routes.inp.Request;
import com.github.stkurilin.routes.util.MatchResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Stanislav  Kurilin
 */
public class RuleImpl implements Rule {
    private final Method method;
    private final UriSpec uriSpec;
    private final TargetSpec targetSpec;

    private final Pattern uriPattern;
    private final List<String> matchersNames;

    public RuleImpl(Method method, UriSpec uriSpec, TargetSpec targetSpec) {
        this.method = method;
        this.uriSpec = uriSpec;
        this.targetSpec = targetSpec;
        uriPattern = pattern(uriSpec);
        matchersNames = names(uriSpec);
    }


    @Override
    public MatchResult<MatchingRule> apply(Request input) {
        if (input.method() != method) return MatchResult.skipped();
        final Matcher matcher = uriPattern.matcher(input.path());
        if (matcher.find()) {
            if (matcher.groupCount() != matchersNames.size()) throw new IllegalStateException(
                    String.format("Expected %s matchers but %s found", matchersNames.size(), matcher.groupCount()));
            final Map<String, String> retrieved = new HashMap<String, String>();
            for (int group = 1; group <= matcher.groupCount(); group++)
                retrieved.put(matchersNames.get(group - 1), matcher.group(group));
            return MatchResult.matched(new MatchingRule(targetSpec, retrieved));
        }
        return MatchResult.skipped();
    }

    private Pattern pattern(UriSpec uriSpec) {
        final StringBuilder patternBuilder = new StringBuilder();
        patternBuilder.append("^");
        for (UriSpec.Item each : uriSpec.path())
            patternBuilder
                    .append("/")
                    .append(each.apply(new UriSpec.ItemVisitor<String>() {
                        public String literal(String value) {
                            return value;
                        }

                        public String matcher(String name) {
                            return "(.*)";
                        }
                    }));

        patternBuilder.append("$");
        return Pattern.compile(patternBuilder.toString());
    }

    private List<String> names(UriSpec uriSpec) {
        final List<String> result = new ArrayList<String>();
        for (UriSpec.Item each : uriSpec.path())
            each.apply(new UriSpec.ItemVisitor<Void>() {
                public Void literal(String value) {
                    return null;
                }

                public Void matcher(String name) {
                    result.add(name);
                    return null;
                }
            });
        return result;
    }
}
