package com.github.stkurilin.routes.examples.webguice.services;

/**
 * @author Stanislav  Kurilin
 */
class FooImpl implements Foo {
    @Override
    public String noArg() {
        return "noArg";
    }

    @Override
    public String oneArg(String arg) {
        return String.format("%s: oneArg (%s)", FooImpl.class.getSimpleName(), arg);
    }

    @Override
    public String oneArgWithMapping(String arg) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void twoArgs(String arg1, String arg2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
