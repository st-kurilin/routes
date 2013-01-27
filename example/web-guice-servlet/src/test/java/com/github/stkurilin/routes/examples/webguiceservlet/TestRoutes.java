package com.github.stkurilin.routes.examples.webguiceservlet;

import com.github.stkurilin.routes.AbstractRoutesTest;
import com.google.inject.servlet.GuiceFilter;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Stanislav  Kurilin
 */
public class TestRoutes extends AbstractRoutesTest {
    @Override
    protected void init() {
        filter(GuiceFilter.class, "/*");
        listener(new Config());
    }

    @Test
    public void test() {
        assertEquals(get("/bb"), "noArg");
    }

    @Test
    public void testPostWithArg() {
        assertEquals(post("/foo/vano", ""), "FooImpl: oneArg (vano)");
    }

    @Test
    public void testRoutingsUsingFile() throws Exception {
        assertEquals(get("/fff/vano"), "FooImpl: oneArg (vano)");
    }
}
