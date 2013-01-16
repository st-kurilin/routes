package com.github.stkurilin.routes;

import com.github.stkurilin.routes.api.*;
import com.github.stkurilin.routes.impl.RoutesBuilder;
import com.github.stkurilin.routes.util.MatchResult;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Stanislav  Kurilin
 */
public class RoutesFilter implements Filter {
    private Routes routes;

    public void init(InstanceFinder instanceFinder, Iterable<Rule> rules) {
        routes = new RoutesBuilder().setInstanceFinder(instanceFinder).setRules(rules).createRoutes();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain) throws IOException, ServletException {

        if (routes == null) throw new RuntimeException("should be initialized");
        final HttpServletRequest req = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final MatchResult<Response> matchResult = routes.apply(new Request() {
            @Override
            public Method method() {
                return Method.valueOf(req.getMethod());
            }

            @Override
            public String path() {
                return req.getPathInfo();
            }
        });
        matchResult.apply(new MatchResult.MatchResultVisitor<Response, Void>() {
            @Override
            public Void matched(Response res) {
                try {
                    final ServletOutputStream outputStream = response.getOutputStream();
                    outputStream.print(res.toBody());
                    outputStream.flush();
                    return null;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Void skipped() {
                try {
                    filterChain.doFilter(servletRequest, servletResponse);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ServletException e) {
                    throw new RuntimeException(e);
                }
                return null;
            }
        });
    }

    @Override
    public void destroy() {
    }
}
