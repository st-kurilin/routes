package com.github.stkurilin.routes;

import com.github.stkurilin.routes.internal.Request;

import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public interface Rule {
    MatchResult<MatchingRule> apply(Request input);

    TargetSpec targetSpec();

    String template();

    class MatchingRule {
        private final TargetSpec targetSpec;
        private final Map<String, String> retrieved;
        private final String template;

        public MatchingRule(TargetSpec targetSpec, Map<String, String> retrieved, String template) {
            this.targetSpec = targetSpec;
            this.retrieved = retrieved;
            this.template = template;
        }

        public TargetSpec getTargetSpec() {
            return targetSpec;
        }

        public Map<String, String> getRetrieved() {
            return retrieved;
        }

        public String getTemplate() {
            return template == null ? "" : template;//todo
        }
    }
}
