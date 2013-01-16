package com.github.stkurilin.routes.impl;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Stanislav  Kurilin
 */
public class JavaMethod {
    private final Object instance;
    private final Method method;
    private final List<Class<?>> classes;

    private JavaMethod(Object instance, Method method, List<Class<?>> classes) {
        this.instance = instance;
        this.method = method;
        this.classes = classes;
    }

    public static JavaMethod from(Object instance, String methodId) {
        final Class<?> clazz = instance.getClass();
        for (final Method each : clazz.getMethods()) {
            if (each.getName().equals(methodId))
                return new JavaMethod(instance, each, Arrays.asList(each.getParameterTypes()));
        }
        throw new RuntimeException(String.format("Couldn't find method %s in %s", methodId, clazz));
    }

    public List<Class<?>> argClasses() {
        return classes;
    }

    public Object apply(Iterable<? extends Object> args) {
        try {
            final List<Object> list = new ArrayList<Object>();
            for (Object o : args) list.add(o);
            return method.invoke(instance, list.toArray());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }
}
