package com.github.stkurilin.routes;

/**
 * @author Stanislav  Kurilin
 */
public interface UriSpec {
    Iterable<Item> path();

    interface Item {
        <R> R apply(ItemVisitor<R> visitor);
    }

    class Literal implements Item {
        private final String value;

        public Literal(String value) {
            this.value = value;
        }

        public <R> R apply(ItemVisitor<R> visitor) {
            return visitor.literal(value);
        }
    }

    class Matcher implements Item {
        public final String name;

        public Matcher(String name) {
            this.name = name;
        }

        public <R> R apply(ItemVisitor<R> visitor) {
            return visitor.matcher(name);
        }
    }

    static class ItemVisitor<R> {
        private final R defaultReturn;

        public ItemVisitor(R defaultReturn) {
            this.defaultReturn = defaultReturn;
        }

        public ItemVisitor() {
            this(null);
        }

        public R literal(String value) {
            return defaultReturn;
        }

        public R matcher(String name) {
            return defaultReturn;
        }
    }
}
