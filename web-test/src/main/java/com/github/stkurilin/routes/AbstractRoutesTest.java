package com.github.stkurilin.routes;

import com.github.stkurilin.routes.servlet.RoutesFilter;
import org.mortbay.jetty.testing.HttpTester;
import org.mortbay.jetty.testing.ServletTester;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import javax.servlet.Filter;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stanislav  Kurilin
 */
public abstract class AbstractRoutesTest {
    {
        System.setProperty("DEBUG", "true");
    }

    private ServletTester tester;
    private HttpTester request;
    private HttpTester response;

    public AbstractRoutesTest() {

    }


    protected void filter(Class<? extends Filter> filterClass, String mapping) {
        this.filter(filterClass, mapping, new HashMap<String, String>());
    }

    protected void filter(Class<? extends Filter> filterClass, String mapping, Map<String, String> initParams) {
        tester.addFilter(filterClass, mapping, 0).setInitParameters(initParams);
    }

    protected void listener(ServletContextListener listener) {
        tester.addEventListener(listener);
    }

    protected String get(String uri) {
        return retrieve(Method.GET, uri, "");
    }

    protected String post(String uri, String content) {
        return retrieve(Method.POST, uri, content);
    }

    protected String put(String uri, String content) {
        return retrieve(Method.PUT, uri, content);
    }

    protected String delete(String uri) {
        return retrieve(Method.DELETE, uri, "");
    }

    public String retrieve(Method method, String uri, String content) {
        try {
            this.request.setMethod(method.toString());
            this.request.setURI(uri);
            this.request.setContent(content);

            response.parse(tester.getResponses(request.generate()));

            return this.response.getContent();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @AfterMethod
    public final void end() throws Exception {
        tester.stop();
        RoutesFilter.initRoutes(null);
    }

    @BeforeMethod
    public final void setUp() throws Exception {
        RoutesFilter.initRoutes(null);
        tester = new ServletTester();
        this.tester.setContextPath("/");
        this.tester.addServlet(FakeServlet.class, "/*");
        this.request = new HttpTester();
        this.request.setHeader("Host", "tester");
        this.request.setVersion("HTTP/1.0");

        this.response = new HttpTester();

        init();
        tester.start();
    }

    protected abstract void init();

    public static class FakeServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            throw new RuntimeException("Should be newer called");
        }
    }
}
