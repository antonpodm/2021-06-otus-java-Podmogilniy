package ru.otus.aop.homework;

import ru.otus.aop.homework.interfaces.ILog;
import ru.otus.aop.homework.interfaces.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class Ioc {

    private Ioc() {
    }

    static ILog createMyClass(ILog myClass) {
        InvocationHandler handler = new DemoInvocationHandler(myClass);
        return (ILog) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{ILog.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final ILog myClass;

        DemoInvocationHandler(ILog myClass) {
            this.myClass = myClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if(method.isAnnotationPresent(Log.class)) {
                System.out.println("invoking method:" + method);
            }
            return method.invoke(myClass, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "myClass=" + myClass +
                    '}';
        }
    }
}
