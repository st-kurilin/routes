package com.github.stkurilin.routes.internal;

/**
 * @author Stanislav  Kurilin
 */
public final class Precond {
    public static void checkState(boolean expr, String msg) {
        if (!expr) throw new IllegalStateException(msg);
    }
}
