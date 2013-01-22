package com.github.stkurilin.routes;

import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;

import javax.servlet.Filter;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author Stanislav  Kurilin
 */
public class IntegrationVerifier {
    {
        System.setProperty("DEBUG", "true");
    }

    private ServletTester tester;
    private HttpTester request;
    private HttpTester response;

    public IntegrationVerifier() {
        tester = new ServletTester();
        this.tester.setContextPath("/");
        this.tester.addServlet(FakeServlet.class, "/*");
        this.request = new HttpTester();
        this.request.setHeader("Host", "tester");
        this.request.setVersion("HTTP/1.0");

        this.response = new HttpTester();


    }

    public void filter(Class<? extends Filter> filterClass, String mapping) {
        tester.addFilter(filterClass, mapping, 0);
    }

    public void listener(ServletContextListener listener) {
        tester.addEventListener(listener);
    }

    public String retrieve(Method method, String uri, String content) {
        try {
            tester.start();

            this.request.setMethod(method.toString());
            this.request.setURI(uri);
            this.request.setContent(content);

            response.parse(tester.getResponses(request.generate()));

            return this.response.getContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void end() {
        try {
            tester.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class FakeServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            throw new RuntimeException("Should be newer called");
        }
    }
}
