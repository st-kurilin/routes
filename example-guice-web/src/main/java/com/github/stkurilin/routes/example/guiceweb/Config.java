package com.github.stkurilin.routes.example.guiceweb;

import com.github.stkurilin.routes.RoutesFilter;
import com.github.stkurilin.routes.Rule;
import com.github.stkurilin.routes.RuleImpl;
import com.github.stkurilin.routes.conf.InstanceFinder;
import com.github.stkurilin.routes.conf.Method;
import com.github.stkurilin.routes.conf.TargetSpec;
import com.github.stkurilin.routes.conf.UriSpec;
import com.github.stkurilin.routes.example.guiceweb.services.Foo;
import com.github.stkurilin.routes.example.guiceweb.services.FooImpl;
import com.github.stkurilin.routes.example.guiceweb.services.ServicesModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import java.util.ArrayList;

/**
 * @author Stanislav  Kurilin
 */
public class Config extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        final RoutesFilter filter = new RoutesFilter();
        final Injector injector = Guice.createInjector(new ServicesModule(), new ServletModule() {
            @Override
            protected void configureServlets() {
                filter("/services/*").through(filter);
            }
        });
        filter.init(new InstanceFinder() {
                        @Override
                        public Object apply(Class instanceClass) {
                            return injector.getInstance(instanceClass);
                        }

                    }, new ArrayList<Rule>() {{
                        add(new RuleImpl(Method.Get, new UriSpec() {
                            @Override
                            public Iterable<Item> path() {
                                return new ArrayList<Item>();
                            }
                        }, new TargetSpec() {
                            @Override
                            public Class<?> clazz() {
                                return FooImpl.class;
                            }

                            @Override
                            public String methodId() {
                                return "bar";
                            }

                            @Override
                            public Iterable<String> args() {
                                return new ArrayList<String>() {{
                                    add("foo");
                                }};
                            }
                        }
                        ));
                    }}
        );
        return injector;
    }
}