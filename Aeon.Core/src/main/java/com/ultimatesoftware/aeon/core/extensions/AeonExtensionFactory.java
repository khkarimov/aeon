package com.ultimatesoftware.aeon.core.extensions;

import org.pf4j.DefaultExtensionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides the same extension instance for the same session.
 */
class AeonExtensionFactory extends DefaultExtensionFactory {

    private ISessionIdProvider sessionIdProvider;

    private Map<String, Map<String, Object>> cache = new HashMap<>();

    private static Logger log = LoggerFactory.getLogger(AeonExtensionFactory.class);

    AeonExtensionFactory(ISessionIdProvider sessionIdProvider) {
        this.sessionIdProvider = sessionIdProvider;
    }

    @Override
    public Object create(Class<?> extensionClass) {
        log.debug("Creating Aeon extension for class {}", extensionClass.getName());
        String currentSessionId = sessionIdProvider.getCurrentSessionId();
        String extensionClassName = extensionClass.getName();

        if (cache.containsKey(currentSessionId)) {
            Map<String, Object> instances = cache.get(currentSessionId);

            if (instances.containsKey(extensionClassName)) {
                return instances.get(extensionClassName);
            }

            Object extension = this.createInstance(extensionClass);
            instances.put(extensionClassName, extension);

            return extension;
        }

        Map<String, Object> instances = new HashMap<>();
        Object extension = this.createInstance(extensionClass);
        instances.put(extensionClassName, extension);
        cache.put(currentSessionId, instances);

        return extension;
    }

    private Object createInstance(Class<?> extensionClass) {

        Method createInstanceMethod;
        try {
            createInstanceMethod = extensionClass.getMethod("createInstance");
        } catch (NoSuchMethodException e) {
            log.debug("Could not find public static method 'createInstance' on " +
                    "the extension class. Falling back to using parameter-less constructor.");

            return createInstanceUsingFallbackStrategy(extensionClass);
        }

        try {
            return createInstanceMethod.invoke(null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.debug("Could not invoke public static method 'createInstance' on " +
                    "the extension class. Falling back to using parameter-less constructor.");

            return createInstanceUsingFallbackStrategy(extensionClass);
        }
    }

    private Object createInstanceUsingFallbackStrategy(Class<?> extensionClass) {
        try {
            extensionClass.getConstructor();
        } catch (NoSuchMethodException noSuchMethodException) {
            String message = String.format("Could not successfully invoke public static " +
                    "method 'createInstance' on the extension %s and there is also " +
                    "no parameter-less constructor present", extensionClass);

            log.error(message);

            throw new IllegalStateException(message);
        }

        return super.create(extensionClass);
    }
}
