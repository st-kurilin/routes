package com.github.stkurilin.routes.examples.webguice.services;

/**
 * @author Stanislav  Kurilin
 */
public interface Foo {
    String noArg();
    String oneArg(String arg);
    String oneArgWithMapping(String arg);
    void twoArgs(String arg1, String arg2);
}
