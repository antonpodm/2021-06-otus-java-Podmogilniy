package ru.otus.aop.homework;

import ru.otus.aop.homework.interfaces.ILog;
import ru.otus.aop.homework.interfaces.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Ioc {

    private Ioc() {
    }

    static Object createMyClass(ILog myClass) {
        InvocationHandler handler = new DemoInvocationHandler(myClass);

        return Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                myClass.getClass().getInterfaces(), handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final ILog myClass;
        private final List<Method> methods;

        DemoInvocationHandler(ILog myClass) {
            this.myClass = myClass;
            this.methods = getAnnotatedMethods(myClass.getClass());
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            for (Method m : methods) {
                if (compareMethods(m, method)) {
                    System.out.println("Executed method: " + method.getName() + ", params: " + Arrays.toString(args));
                    break;
                }
            }
            return method.invoke(myClass, args);
        }
    }

    static List<Method> getAnnotatedMethods(Class clazz) {
        return Arrays.stream(clazz.getMethods()).filter(method -> method.isAnnotationPresent(Log.class)).collect(Collectors.toList());
    }

    static boolean compareMethods(Method m1, Method m2) {
        if (!m1.getName().equals(m2.getName())) {
            return false;
        }
        if (!Arrays.equals(m1.getParameterTypes(), m2.getParameterTypes())) {
            return false;
        }
        return true;
    }
}
