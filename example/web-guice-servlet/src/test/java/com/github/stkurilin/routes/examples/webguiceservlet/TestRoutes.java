package com.github.stkurilin.routes.examples.webguiceservlet;

import com.github.stkurilin.routes.examples.webguiceservlet.services.FakeServlet;
import com.google.inject.servlet.GuiceFilter;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Stanislav  Kurilin
 */
public class TestRoutes {
    {
        System.setProperty("DEBUG", "true");
    }

    private ServletTester tester;
    private HttpTester request;
    private HttpTester response;

    @BeforeMethod
    public void setUp() throws Exception {
        this.tester = new ServletTester();
        this.tester.setContextPath("/");
        this.tester.addEventListener(new Config());
        this.tester.addFilter(GuiceFilter.class, "/*", 0);
        this.tester.addServlet(FakeServlet.class, "/*");
        this.tester.start();

        this.request = new HttpTester();
        this.response = new HttpTester();
        this.request.setMethod("GET");
        this.request.setHeader("Host", "tester");
        this.request.setVersion("HTTP/1.0");
    }

    @Test
    public void test() throws Exception {
        this.request.setURI("/bb");
        this.response.parse(tester.getResponses(request.generate()));
        assertEquals(this.response.getStatus(), 200);
        //assertEquals(this.response.getContent(), "noArg");
    }
}
