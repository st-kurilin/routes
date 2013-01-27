package com.github.stkurilin.routes.examples.web;

/**
 * @author Stanislav  Kurilin
 */
public class Foo {
    public String bar(String arg) {
        return String.format("Foo.bar was invoked with argument : %s", arg);
    }
}
