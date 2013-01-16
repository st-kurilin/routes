package com.github.stkurilin.routes;

import com.github.stkurilin.routes.conf.TargetSpec;
import com.github.stkurilin.routes.inp.Request;
import com.github.stkurilin.routes.util.MatchResult;

import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public interface Rule {
    MatchResult<MatchingRule> apply(Request input);

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
