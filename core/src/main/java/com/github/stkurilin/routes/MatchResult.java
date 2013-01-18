package com.github.stkurilin.routes;

/**
 * @author Stanislav  Kurilin
 */
public abstract class MatchResult<T> {
    public abstract <R> R apply(MatchResultVisitor<? super T, R> input);

    public interface MatchResultVisitor<T, R> {
        R matched(T res);

        R skipped();
    }

    public static <T> MatchResult<T> matched(T input) {
        return new Matched<T>(input);
    }

    private static final Skipped<?> SKIPPED = new Skipped<Object>();

    @SuppressWarnings("unchecked") //empty pattern
    public static <T> MatchResult<T> skipped() {
        return (MatchResult<T>) SKIPPED;
    }

    public static MatchResult.MatchResultVisitor<Object, Boolean> matchedPredicate() {
        return IS_MATCHED_PREDICATE;
    }


    private static final MatchResult.MatchResultVisitor<Object, Boolean> IS_MATCHED_PREDICATE = new MatchResult.MatchResultVisitor<Object, Boolean>() {
        @Override
        public Boolean matched(Object input) {
            return true;
        }

        @Override
        public Boolean skipped() {
            return false;
        }
    };

    private static class Matched<T> extends MatchResult<T> {
        final T res;

        private Matched(T res) {
            this.res = res;
        }

        @Override
        public <R> R apply(MatchResultVisitor<? super T, R> input) {
            return input.matched(res);
        }
    }

    private static class Skipped<T> extends MatchResult<T> {
        @Override
        public <R> R apply(MatchResultVisitor<? super T, R> input) {
            return input.skipped();
        }
    }

    private MatchResult() {
    }
}
