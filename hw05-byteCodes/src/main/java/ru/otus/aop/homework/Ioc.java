package ru.otus.aop.homework;

import ru.otus.aop.homework.interfaces.ILog;
import ru.otus.aop.homework.interfaces.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
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
        private final List<String> methodsSignatures = new ArrayList<>();

        DemoInvocationHandler(ILog myClass) {
            this.myClass = myClass;
            getAnnotatedMethods(myClass.getClass())
                    .stream()
                    .map(Ioc::createMethodSignature)
                    .forEach(methodsSignatures::add);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String currentMethodSignature = createMethodSignature(method);
            if (methodsSignatures.stream().anyMatch(currentMethodSignature::equals)) {
                System.out.println("Executed method: " + method.getName() + ", params: " + Arrays.toString(args));
            }
            return method.invoke(myClass, args);
        }
    }

    static List<Method> getAnnotatedMethods(Class clazz) {
        return Arrays.stream(clazz.getMethods()).
                filter(method -> method.isAnnotationPresent(Log.class)).
                collect(Collectors.toList());
    }

    static String createMethodSignature(Method m) {
        return m.getName() + Arrays.toString(m.getParameterTypes());
    }
}
