package com.github.stkurilin.routes;

import com.github.stkurilin.routes.conf.InstanceFinder;
import com.github.stkurilin.routes.conf.Method;
import com.github.stkurilin.routes.inp.Request;
import com.github.stkurilin.routes.out.Response;
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
    private InstanceFinder instanceFinder;

    public void init(InstanceFinder instanceFinder, Iterable<Rule> rules) {
        this.instanceFinder = instanceFinder;
        routes = new RoutesBuilder().setRules(rules).createRoutes();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest servletRequest,
                         final ServletResponse servletResponse,
                         final FilterChain filterChain) throws IOException, ServletException {
        if (instanceFinder == null) throw new RuntimeException("instanceFinder should be specified");
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
