package com.github.stkurilin.routes.internal;

/**
 * @author Stanislav  Kurilin
 */
public interface Retriever {
    Object apply(Object input, Class<?> target);

    Retriever DEFAULT = new Retriever() {
        @Override
        public Object apply(Object input, Class<?> target) {
            if (target.getClass().equals(input.getClass())) return input;
            if (target.equals(String.class)) return input.toString();
            //if (target.equals(Integer.class) && input instanceof String) return Integer.parseInt(input.toString());
            if (target.equals(Long.class) && input instanceof String) return Long.parseLong(input.toString());

            return null;
        }
    };
}
