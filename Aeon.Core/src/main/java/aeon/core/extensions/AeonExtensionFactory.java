package aeon.core.extensions;

import org.pf4j.DefaultExtensionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides the same extension instance for the same session.
 */
class AeonExtensionFactory extends DefaultExtensionFactory {

    private ISessionIdProvider sessionIdProvider;

    private Map<String, Map<String, Object>> cache = new HashMap<>();

    AeonExtensionFactory(ISessionIdProvider sessionIdProvider) {
        this.sessionIdProvider = sessionIdProvider;
    }

    @Override
    public Object create(Class<?> extensionClass) {
        String currentSessionId = sessionIdProvider.getCurrentSessionId();
        String extensionClassName = extensionClass.getName();

        if (cache.containsKey(currentSessionId)) {
            Map<String, Object> instances = cache.get(currentSessionId);

            if (instances.containsKey(extensionClassName)) {
                return instances.get(extensionClassName);
            }

            Object extension = super.create(extensionClass);
            instances.put(extensionClassName, extension);

            return extension;
        }

        Map<String, Object> instances = new HashMap<>();
        Object extension = super.create(extensionClass);
        instances.put(extensionClassName, extension);
        cache.put(currentSessionId, instances);

        return extension;
    }
}
