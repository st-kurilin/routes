package com.github.stkurilin.routes.examples.webguiceservlet;

import com.github.stkurilin.routes.IntegrationVerifier;
import com.github.stkurilin.routes.Method;
import com.google.inject.servlet.GuiceFilter;
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
        integrationVerifier.filter(GuiceFilter.class, "/*");
        integrationVerifier.listener(new Config());
    }

    @AfterMethod
    public void tearDown() {
        integrationVerifier.end();

    }

    @Test
    public void test() {
        assertEquals(integrationVerifier.retrieve(Method.GET, "/bb", ""), "noArg");
    }
}
