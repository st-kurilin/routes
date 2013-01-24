package com.github.stkurilin.routes.examples.webresetguicejackson;

import com.github.stkurilin.routes.IntegrationVerifier;
import com.github.stkurilin.routes.Method;
import com.github.stkurilin.routes.examples.webresetguicejackson.entity.Person;
import com.github.stkurilin.routes.servlet.RoutesFilter;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

/**
 * @author Stanislav  Kurilin
 */
public class PersonServiceTest {
    @Test
    public void testCreate() {
        assertLong(integrationVerifier.retrieve(Method.POST, "/persons", data("Alan", 100)));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFind() throws IOException {
        final long id = Long.parseLong(integrationVerifier.retrieve(Method.POST, "/persons", data("Bob", 70)));
        final String fromServer = integrationVerifier.retrieve(Method.GET, personUri(id), "");
        System.out.println(">>>" + fromServer);
        final Person deserialized = new ObjectMapper().readValue(fromServer, Person.class);
        assertEquals(deserialized, new Person(id, "Bob", 70));
    }

    @Test(dependsOnMethods = {"testCreate", "testFind"})
    public void testUpdate() throws IOException {
        final long id = Long.parseLong(integrationVerifier.retrieve(Method.POST, "/persons", data("Bob", 70)));
        integrationVerifier.retrieve(Method.PUT, personUri(id), data("Bob", 71));
        final String fromServer = integrationVerifier.retrieve(Method.GET, personUri(id), "");
        final Person deserialized = new ObjectMapper().readValue(fromServer, Person.class);
        assertEquals(deserialized, new Person(id, "Bob", 71));
    }

    @Test(dependsOnMethods = {"testCreate", "testFind"})
    public void testDelete() throws IOException {
        final long id = Long.parseLong(integrationVerifier.retrieve(Method.POST, "/persons", data("Bob", 70)));
        integrationVerifier.retrieve(Method.DELETE, personUri(id), "");
        final String fromServer = integrationVerifier.retrieve(Method.GET, personUri(id), "");
        assertEquals(fromServer, "null"); //todo
    }


    private String personUri(long id) {
        return String.format("/persons/%s", id);
    }

    private String data(String name, int age) {
        return String.format("{\"name\":\"%s\", \"age\":\"%s\"}", name, age);
    }


    private IntegrationVerifier integrationVerifier;

    @BeforeMethod
    public void setUp() {
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
