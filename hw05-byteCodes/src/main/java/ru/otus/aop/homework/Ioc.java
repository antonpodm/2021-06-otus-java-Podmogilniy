package ru.otus.aop.homework;

import ru.otus.aop.homework.interfaces.ILog;
import ru.otus.aop.homework.interfaces.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

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

        DemoInvocationHandler(ILog myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(Log.class)) {
                System.out.println("Executed method: " + method.getName() + ", params: " + Arrays.toString(args));
            }
            return method.invoke(myClass, args);
        }
    }
}
