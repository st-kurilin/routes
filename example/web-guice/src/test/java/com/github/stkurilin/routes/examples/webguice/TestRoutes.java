package com.github.stkurilin.routes.examples.webguice;

import com.github.stkurilin.routes.IntegrationVerifier;
import com.github.stkurilin.routes.Method;
import com.github.stkurilin.routes.servlet.RoutesFilter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Stanislav  Kurilin
 */
public class TestRoutes {
    private IntegrationVerifier integrationVerifier;

    @BeforeMethod
    public void setUp() throws Exception {
        integrationVerifier = new IntegrationVerifier();
        integrationVerifier.filter(RoutesFilter.class, "/*");
        integrationVerifier.listener(new Config());
    }

    @AfterMethod
    public void tearDown() {
        integrationVerifier.end();
        RoutesFilter.initRoutes(null);
    }

    @Test
    public void test() {
        assertEquals(integrationVerifier.retrieve(Method.GET, "/bb", ""), "noArg");
    }

    @Test
    public void testPostWithArg() {
        assertEquals(integrationVerifier.retrieve(Method.POST, "/foo/vano", ""), "FooImpl: oneArg (vano)");
    }

    @Test
    public void testRoutingsUsingFile() throws Exception {
        assertEquals(integrationVerifier.retrieve(Method.GET, "/fff/vano", ""), "FooImpl: oneArg (vano)");
    }
}
