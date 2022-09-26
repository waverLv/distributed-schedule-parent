package com.lv.distributed.loader;

import com.lv.distributed.annotation.Activate;
import com.lv.distributed.annotation.SPI;
import com.lv.distributed.factory.ExtensionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ProjectName: ExtensionLoader
 * @Author: lvminghui
 * @Description: 扩展类加载器
 * @Date: 2022/9/15 14:01
 * @Version: 1.0
 */
public final class ExtensionLoader<T> {
    private static final Logger log = LoggerFactory.getLogger(ExtensionLoader.class);

    private static final String SERVICE_DIRECTORY = "META-INF/services/";

    private static final Map<Class<?>, ExtensionLoader<?>> LOADERS = new ConcurrentHashMap<>();

    private final Class<T> clazz;

    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

    private final Map<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();

    private final Map<Class<?>, Object> activateInstances = new ConcurrentHashMap<>();

    private String cachedDefaultName;

    /**
     * Instantiates a new Extension loader.
     *
     * @param clazz the clazz.
     */
    private ExtensionLoader(final Class<T> clazz) {
        this.clazz = clazz;
        if (clazz != ExtensionFactory.class) {
            ExtensionLoader.getExtensionLoader(ExtensionFactory.class).getExtensionClasses();
        }
    }

    /**
     * Gets extension loader.
     *
     * @param <T>   the type parameter
     * @param clazz the clazz
     * @return the extension loader.
     */
    public static <T> ExtensionLoader<T> getExtensionLoader(final Class<T> clazz) {
        if (clazz == null) {
            throw new NullPointerException("extension clazz is null");
        }
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException("extension clazz (" + clazz + ") is not interface!");
        }
        if (!clazz.isAnnotationPresent(SPI.class)) {
            throw new IllegalArgumentException("extension clazz (" + clazz + ") without @" + SPI.class + " Annotation");
        }
        ExtensionLoader<T> extensionLoader = (ExtensionLoader<T>) LOADERS.get(clazz);
        if (extensionLoader != null) {
            return extensionLoader;
        }
        LOADERS.putIfAbsent(clazz, new ExtensionLoader<>(clazz));
        return (ExtensionLoader<T>) LOADERS.get(clazz);
    }

    /**
     * Gets default Activate.
     *
     * @return the default Activate.
     */
    public T getDefaultActivate() {
        getExtensionClasses();
        if (StringUtils.isBlank(cachedDefaultName)) {
            return null;
        }
        return getActivate(cachedDefaultName);
    }

    /**
     * Gets Activate.
     *
     * @param name the name
     * @return the Activate.
     */
    public T getActivate(final String name) {
        if (StringUtils.isBlank(name)) {
            throw new NullPointerException("get Activate name is null");
        }
        Holder<Object> objectHolder = cachedInstances.get(name);
        if (objectHolder == null) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            objectHolder = cachedInstances.get(name);
        }
        Object value = objectHolder.getValue();
        if (value == null) {
            synchronized (cachedInstances) {
                value = objectHolder.getValue();
                if (value == null) {
                    value = createExtension(name);
                    objectHolder.setValue(value);
                }
            }
        }
        return (T) value;
    }

    public Set<String> getSupportedExtensions() {
        Map<String, Class<?>> clazzes = getExtensionClasses();
        return Collections.unmodifiableSet(new TreeSet<>(clazzes.keySet()));
    }

    @SuppressWarnings("unchecked")
    private T createExtension(final String name) {
        Class<?> aClass = getExtensionClasses().get(name);
        if (aClass == null) {
            throw new IllegalArgumentException("name is error");
        }
        Object o = activateInstances.get(aClass);
        if (o == null) {
            try {
                activateInstances.putIfAbsent(aClass, aClass.newInstance());
                o = activateInstances.get(aClass);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException("Extension instance(name: " + name + ", class: "
                        + aClass + ")  could not be instantiated: " + e.getMessage(), e);

            }
        }
        return (T) o;
    }

    /**
     * Gets extension classes.
     *
     * @return the extension classes
     */
    public Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = cachedClasses.getValue();
        if (classes == null) {
            synchronized (cachedClasses) {
                classes = cachedClasses.getValue();
                if (classes == null) {
                    classes = loadExtensionClass();
                    cachedClasses.setValue(classes);
                }
            }
        }
        return classes;
    }

    private Map<String, Class<?>> loadExtensionClass() {
        SPI annotation = clazz.getAnnotation(SPI.class);
        if (annotation != null) {
            String value = annotation.value();
            if (StringUtils.isNotBlank(value)) {
                cachedDefaultName = value;
            }
        }
        Map<String, Class<?>> classes = new HashMap<>(16);
        loadDirectory(classes);
        return classes;
    }

    /**
     * Load files under SERVICE_DIRECTORY.
     */
    private void loadDirectory(final Map<String, Class<?>> classes) {
        String fileName = SERVICE_DIRECTORY + clazz.getName();
        try {
            ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
            Enumeration<URL> urls = classLoader != null ? classLoader.getResources(fileName)
                    : ClassLoader.getSystemResources(fileName);
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    loadResources(classes, url);
                }
            }
        } catch (IOException t) {
            log.error("load extension class error {}", fileName, t);
        }
    }

    private void loadResources(final Map<String, Class<?>> classes, final URL url) throws IOException {
        try (InputStream inputStream = url.openStream()) {
            Properties properties = new Properties();
            properties.load(inputStream);
            properties.forEach((k, v) -> {
                String name = (String) k;
                String classPath = (String) v;
                if (StringUtils.isNotBlank(name) && StringUtils.isNotBlank(classPath)) {
                    try {
                        loadClass(classes, name, classPath);
                    } catch (ClassNotFoundException e) {
                        throw new IllegalStateException("load extension resources error", e);
                    }
                }
            });
        } catch (IOException e) {
            throw new IllegalStateException("load extension resources error", e);
        }
    }

    private void loadClass(final Map<String, Class<?>> classes,
                           final String name, final String classPath) throws ClassNotFoundException {
        Class<?> subClass = Class.forName(classPath);
        if (!clazz.isAssignableFrom(subClass)) {
            throw new IllegalStateException("load extension resources error," + subClass + " subtype is not of " + clazz);
        }
        Activate annotation = subClass.getAnnotation(Activate.class);
        if (annotation == null) {
            throw new IllegalStateException("load extension resources error," + subClass + " with Activate annotation");
        }
        Class<?> oldClass = classes.get(name);
        if (oldClass == null) {
            classes.put(name, subClass);
        } else if (oldClass != subClass) {
            throw new IllegalStateException("load extension resources error,Duplicate class " + clazz.getName() + " name " + name + " on " + oldClass.getName() + " or " + subClass.getName());
        }
    }




    /**
     * The type Holder.
     *
     * @param <T> the type parameter.
     */
    public static class Holder<T> {

        private volatile T value;

        /**
         * Gets value.
         *
         * @return the value
         */
        public T getValue() {
            return value;
        }

        /**
         * Sets value.
         *
         * @param value the value
         */
        public void setValue(final T value) {
            this.value = value;
        }
    }
}
