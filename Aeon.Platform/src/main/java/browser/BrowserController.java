package browser;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.common.Capability;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.Configuration;
import aeon.core.testabstraction.product.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController("/")
public class BrowserController {

    private Configuration configuration;
    private AutomationInfo automationInfo;

    private static <T extends Product> IAdapterExtension loadPlugins() throws RuntimeException {
        List<IAdapterExtension> extensions = Aeon.getExtensions(IAdapterExtension.class);
        for (IAdapterExtension extension : extensions) {
            if (extension.getProvidedCapability() == Capability.WEB) {
                return extension;
            }
        }

        throw new RuntimeException("No valid adapter found");
    }

    private IAdapter createAdapter(IAdapterExtension plugin) {
        return plugin.createAdapter(configuration);
    }

    @GetMapping("launch")
    public void launch() throws InstantiationException, IllegalAccessException, IOException {
        IAdapterExtension plugin = loadPlugins();

        IDriver driver;
        IAdapter adapter;

        configuration = plugin.getConfiguration();
        adapter = createAdapter(plugin);

        driver = (IDriver)configuration.getDriver().newInstance();
        driver.configure(adapter, configuration);

        this.automationInfo = new AutomationInfo(configuration, driver, adapter);

        System.out.println("launch");
    }

    @GetMapping("close")
    public void close() {

    }
}
