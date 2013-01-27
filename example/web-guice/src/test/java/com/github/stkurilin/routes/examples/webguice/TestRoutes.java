package com.github.stkurilin.routes.examples.webguice;

import com.github.stkurilin.routes.AbstractRoutesTest;
import com.github.stkurilin.routes.servlet.RoutesFilter;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Stanislav  Kurilin
 */
public class TestRoutes extends AbstractRoutesTest {
    public TestRoutes() {
        super("/webguice");
    }

    @Override
    protected void init() {
        filter(RoutesFilter.class, "/*");
        listener(new Config());
    }

    @Test
    public void test() {
        assertEquals(get("/webguice/bb"), "noArg");
    }

    @Test
    public void testPostWithArg() {
        assertEquals(post("/webguice/foo/vano", ""), "FooImpl: oneArg (vano)");
    }

    @Test
    public void testRoutingsUsingFile() throws Exception {
        assertEquals(get("/webguice/fff/vano"), "FooImpl: oneArg (vano)");
    }

    @Test
    public void testTemplateUsage() throws Exception {
        assertEquals(get("/webguice/template/vano"), " Hello World!");
    }
}
