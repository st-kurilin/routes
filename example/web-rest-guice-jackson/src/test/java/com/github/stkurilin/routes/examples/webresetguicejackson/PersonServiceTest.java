package com.github.stkurilin.routes.examples.webresetguicejackson;

import com.github.stkurilin.routes.IntegrationVerifier;
import com.github.stkurilin.routes.Method;
import com.github.stkurilin.routes.examples.webresetguicejackson.entity.Person;
import com.github.stkurilin.routes.servlet.RoutesFilter;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * @author Stanislav  Kurilin
 */
public class PersonServiceTest {
    @Test
    public void testCreate() throws Exception {
        assertLong(create("Alan", 100));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFind() throws Exception {
        final long id = Long.parseLong(create("Bob", 70));
        final String fromServer = integrationVerifier.retrieve(Method.GET, String.format("/persons/%s", id), "");
        System.out.println(">>>" + fromServer);
        final Person deserialized = new ObjectMapper().readValue(fromServer, Person.class);
        assertEquals(deserialized, new Person(id, "Bob", 70));
    }

    private String create(String name, int age) {
        return integrationVerifier.retrieve(Method.POST, "/persons", String.format("{\"name\":\"%s\", \"age\":\"%s\"}", name, age));
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
