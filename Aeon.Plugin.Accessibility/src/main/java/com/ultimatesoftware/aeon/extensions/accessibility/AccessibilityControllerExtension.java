package com.ultimatesoftware.aeon.extensions.accessibility;

import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import org.pf4j.Extension;
import org.pf4j.ExtensionPoint;

import java.util.List;

/**
 * Extension for controlling accessibility plugins.
 */
@Extension
public class AccessibilityControllerExtension implements ExtensionPoint {

    /**
     * Call all accessibility plugins to run accessibility tests.
     *
     * @param pageName The page name.
     */
    public void runAccessibilityTests(String pageName) {

        List<IAccessibilityExtension> testExecutionExtensions = Aeon.getExtensions(IAccessibilityExtension.class);

        for (IAccessibilityExtension accessibilityExtension : testExecutionExtensions) {
            accessibilityExtension.runAccessibilityTests(pageName);
        }
    }
}
