package com.github.stkurilin.routes.routes;

import java.util.Arrays;

/**
 * @author Stanislav  Kurilin
 */
public final class New {
    public static <T> Iterable<T> iterable(T... args) {
        return Arrays.asList(args);
    }
}
