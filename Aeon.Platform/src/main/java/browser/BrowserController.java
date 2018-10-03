package browser;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.consumers.DelegateRunnerFactory;
import aeon.core.common.Capability;
import aeon.core.common.helpers.AjaxWaiter;
import aeon.core.framework.abstraction.adapters.IAdapter;
import aeon.core.framework.abstraction.adapters.IAdapterExtension;
import aeon.core.framework.abstraction.drivers.IDriver;
import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.Configuration;
import aeon.core.testabstraction.product.Product;
import aeon.core.testabstraction.product.WebConfiguration;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;

/**
 * Controller for browser.
 */
@RestController("/")
public class BrowserController {

    private Configuration configuration;
    private AutomationInfo automationInfo;
    private WebCommandExecutionFacade commandExecutionFacade;
    private long timeout;
    private long throttle;
    private long ajaxTimeout;
    private DelegateRunnerFactory delegateRunnerFactory;
    private AjaxWaiter ajaxWaiter;


    private Map<ObjectId, AutomationInfo> sessionTable = new HashMap<>();
    // synchronize w Collections.synchronizedMap? or ConcurrentHashMap?


    /**
     *
     * @param <T>
     * @return
     * @throws RuntimeException
     */
    private static <T extends Product> IAdapterExtension loadPlugins() throws RuntimeException {
        List<IAdapterExtension> extensions = Aeon.getExtensions(IAdapterExtension.class);

        for (IAdapterExtension extension : extensions) {
            if (extension.getProvidedCapability() == Capability.WEB) {
                return extension;
            }
        }

        throw new RuntimeException("No valid adapter found");
    }

    /**
     *
     * @param plugin
     * @return
     */
    private IAdapter createAdapter(IAdapterExtension plugin) {
        return plugin.createAdapter(configuration);
    }



    /**
     * Creates a new session.
     * @param body Body
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    @PostMapping("sessions")
    public ResponseEntity createSession(@RequestBody CreateSessionBody body) throws Exception {
        ObjectId sessionId = new ObjectId();
        body.setSessionId(sessionId);

        System.out.println("sessionID = " + sessionId.toString());
        System.out.println("sessionID = " + body.getSessionId().toString());


        IAdapterExtension plugin = loadPlugins();

        IDriver driver;
        IAdapter adapter;

        configuration = plugin.getConfiguration();

        if (body.getSettings() != null) {
            configuration.setProperties(body.getSettings());
        }

        adapter = createAdapter(plugin);

        driver = (IDriver) configuration.getDriver().newInstance();
        driver.configure(adapter, configuration);



        this.automationInfo = new AutomationInfo(configuration, driver, adapter);

        timeout = (long) configuration.getDouble(Configuration.Keys.TIMEOUT, 10);
        throttle = (long) configuration.getDouble(Configuration.Keys.THROTTLE, 100);
        ajaxTimeout = (long) configuration.getDouble(WebConfiguration.Keys.AJAX_TIMEOUT, 20);

        delegateRunnerFactory = new DelegateRunnerFactory(Duration.ofMillis(throttle), Duration.ofSeconds(timeout));
        ajaxWaiter = new AjaxWaiter(this.automationInfo.getDriver(), Duration.ofSeconds(ajaxTimeout));

        commandExecutionFacade = new WebCommandExecutionFacade(delegateRunnerFactory, ajaxWaiter);

        automationInfo.setCommandExecutionFacade(commandExecutionFacade);



        Properties properties = new Properties();
        properties.setProperty("hello", "world");
        properties.setProperty("hi", "there");



        //sessionId = body.getSessionId();
        sessionTable.put(sessionId, automationInfo);

        return new ResponseEntity<>(sessionId, HttpStatus.CREATED);
    }

    /**
     * Executes a given command.
     * @param sessionId Session ID
     * @param command Command
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    @PostMapping("sessions/{sessionId}/{command}")
    public ResponseEntity executeCommand(@PathVariable ObjectId sessionId, @PathVariable String command) throws Exception {
        automationInfo = sessionTable.get(sessionId);


        // guidelines: all lowercase & hyphenate multi-word paths. maybe we can use this to get correct capitalization

        // assume string has correct capitalization
        String commandString = command + "Command";
        //String commandString = command.substring(0, 1).toUpperCase() + command.substring(1) + "Command";

        //System.out.printf(commandString);

        Class cmd;


        // right now, ignores mobile commands
        if (commandString.equals("QuitCommand") || commandString.equals("CloseCommand")) {
            cmd = Class.forName("aeon.core.command.execution.commands." + commandString);
            sessionTable.remove(sessionId);
        } else {
            cmd = Class.forName("aeon.core.command.execution.commands.web." + commandString);
        }

        commandExecutionFacade.execute(automationInfo, (Command) cmd.newInstance());

        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }
}
