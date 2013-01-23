package com.github.stkurilin.routes.servlet;

import com.github.stkurilin.routes.*;
import com.github.stkurilin.routes.internal.Request;
import com.github.stkurilin.routes.internal.Response;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Stanislav  Kurilin
 */
public class RoutesFilter implements Filter {
    private static Routes routes;

    public static void initRoutes(Routes routes) {
        if (routes != null && RoutesFilter.routes != null)
            throw new RuntimeException("Several rules sources not implemented yet");
        RoutesFilter.routes = routes;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        final String config = filterConfig.getInitParameter("config");
        if (config != null) initRoutes(new RoutesBuilder().setRules(new RulesReader().apply(config)).build());
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

            @Override
            public InputStream content() {
                try {
                    return req.getInputStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
