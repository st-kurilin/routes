package com.github.stkurilin.routes;

import com.github.stkurilin.routes.internal.Request;

import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public interface Rule {
    MatchResult<MatchingRule> apply(Request input);
    TargetSpec targetSpec();
    class MatchingRule {
        private final TargetSpec targetSpec;
        private final Map<String, String> retrieved;

        public MatchingRule(TargetSpec targetSpec, Map<String, String> retrieved) {
            this.targetSpec = targetSpec;
            this.retrieved = retrieved;
        }

        public TargetSpec getTargetSpec() {
            return targetSpec;
        }

        public Map<String, String> getRetrieved() {
            return retrieved;
        }
    }
}
