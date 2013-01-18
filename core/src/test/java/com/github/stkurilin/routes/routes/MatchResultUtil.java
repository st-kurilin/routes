package com.github.stkurilin.routes.routes;


import com.github.stkurilin.routes.TargetSpec;
import com.github.stkurilin.routes.MatchResult;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Stanislav  Kurilin
 */
final class MatchResultUtil {
    //mockito didn't used since we "cannot inline mock creation (mock()) call inside a thenReturn method (see issue 53)"
    private static final TargetSpec TARGET_SPEC = new TargetSpec(){
        @Override
        public Class<?> clazz() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String methodId() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Iterable<String> args() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }
    };

    static MatchResult matched() {
        return MatchResult.matched(null);
    }

    static MatchResult skipped() {
        return MatchResult.skipped();
    }

    static void assertSkipped(MatchResult<?> res) {
        assertFalse(res.apply(MatchResult.matchedPredicate()));
    }

    static void assertMatched(MatchResult<?> res) {
        assertTrue(res.apply(MatchResult.matchedPredicate()));
    }
}
