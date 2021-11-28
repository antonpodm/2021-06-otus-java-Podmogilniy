package ru.otus.appcontainer;

import ru.otus.appcontainer.api.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        fillComponents(configClass);
    }

    private void fillComponents(Class<?> configClass) {
        try {
            var configInstance = configClass.getDeclaredConstructor().newInstance();
            Arrays.stream(configClass.getDeclaredMethods())
                    .filter(m -> m.isAnnotationPresent((AppComponent.class)))
                    .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()))
                    .forEach(m -> {
                        if (!m.canAccess(configInstance)) {
                            m.setAccessible(true);
                        }
                        var params = m.getParameterTypes();
                        var args = new Object[params.length];

                        for (int i = 0; i < params.length; i++) {
                            args[i] = getAppComponent(params[i]);
                        }

                        try {
                            var component = m.invoke(configInstance, args);
                            var name = m.getAnnotation(AppComponent.class).name();
                            appComponents.add(component);
                            appComponentsByName.put(name, component);
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                            throw new MyRuntimeException(ex);
                        }
                    });
        } catch (Exception ex) {
            throw new MyRuntimeException(ex);
        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream()
                .filter(c -> componentClass.isAssignableFrom(c.getClass()))
                .findFirst()
                .orElseThrow(() -> new NoSuchComponentException(componentClass.getName()));
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        var component = appComponentsByName.get(componentName);
        if (!Objects.isNull(component)) {
            return (C) component;
        }
        throw new NoSuchComponentException(componentName);
    }
}
