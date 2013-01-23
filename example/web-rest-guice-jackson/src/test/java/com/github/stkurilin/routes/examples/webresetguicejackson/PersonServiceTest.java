package com.github.stkurilin.routes.examples.webresetguicejackson;

import com.github.stkurilin.routes.IntegrationVerifier;
import com.github.stkurilin.routes.Method;
import com.github.stkurilin.routes.servlet.RoutesFilter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

/**
 * @author Stanislav  Kurilin
 */
public class PersonServiceTest {
    @Test
    public void testCreate() throws Exception {
        final String retrieve = integrationVerifier.retrieve(Method.POST, "/persons", "{name:'Alan', age:'10'}");
        System.out.println(">"+retrieve+"<");
    }


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

    private void assertLong(String s) {
        try {
            Long.parseLong(s);
        } catch (NumberFormatException e) {
            fail("Could not parse", e);
        }
    }
}
