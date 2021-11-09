package ru.otus.cachehw;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.HomeWork;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {

    //Надо реализовать эти методы
    private final WeakHashMap<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(MyCache.class);

    public MyCache() {
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        for (var listener : listeners) {
            try {
                listener.notify(key, value, "put");
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void remove(K key) {
        V value = cache.remove(key);
        for (var listener : listeners) {
            try {
                listener.notify(key, value, "remove");
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        for (var listener : listeners) {
            try {
                listener.notify(key, value, "get");
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
