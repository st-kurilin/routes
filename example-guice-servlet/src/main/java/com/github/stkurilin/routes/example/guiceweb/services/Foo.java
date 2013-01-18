package com.github.stkurilin.routes.example.guiceweb.services;

/**
 * @author Stanislav  Kurilin
 */
public interface Foo {
    String noArg();
    String oneArg(String arg);
    String oneArgWithMapping(String arg);
    void twoArgs(String arg1, String arg2);
}
