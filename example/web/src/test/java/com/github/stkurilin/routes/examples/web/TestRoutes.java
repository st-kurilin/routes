package com.github.stkurilin.routes.examples.web;

import com.github.stkurilin.routes.AbstractRoutesTest;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableMap.of;
import static org.testng.Assert.assertEquals;

/**
 * @author Stanislav  Kurilin
 */
public class TestRoutes extends AbstractRoutesTest {


    @Override
    protected void init() {
        filter(com.github.stkurilin.routes.servlet.RoutesFilter.class, "/*", of("config", "routes/config.routes"));
    }

    @Test
    public void test() {
        assertEquals(get("/foo/bar"), "Foo.bar was invoked with argument : bar");
    }
}
