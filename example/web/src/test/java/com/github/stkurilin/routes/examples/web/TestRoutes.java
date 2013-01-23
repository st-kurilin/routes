package com.github.stkurilin.routes.examples.web;

import com.github.stkurilin.routes.IntegrationVerifier;
import com.github.stkurilin.routes.Method;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.google.common.collect.ImmutableMap.of;
import static org.testng.Assert.assertEquals;

/**
 * @author Stanislav  Kurilin
 */
public class TestRoutes {
    private IntegrationVerifier integrationVerifier;

    @BeforeMethod
    public void setUp() throws Exception {
        integrationVerifier = new IntegrationVerifier();
        integrationVerifier.filter(com.github.stkurilin.routes.servlet.RoutesFilter.class, "/*",
                of("config", "routes/config.routes"));
    }

    @AfterMethod
    public void tearDown() {
        integrationVerifier.end();

    }

    @Test
    public void test() {
        assertEquals(integrationVerifier.retrieve(Method.GET, "/foo/bar", ""), "Foo.bar was invoked with argument : bar");
    }
}
