package com.github.stkurilin.routes.conf;

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

    interface ItemVisitor<R> {
        R literal(String value);

        R matcher(String name);
    }
}
