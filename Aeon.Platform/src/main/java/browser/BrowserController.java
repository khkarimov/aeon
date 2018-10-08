package browser;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import aeon.core.command.execution.commands.Command;
import aeon.core.command.execution.commands.CommandWithReturn;
import aeon.core.command.execution.commands.initialization.WebCommandInitializer;
import aeon.core.command.execution.commands.web.WebControlFinder;
import aeon.core.command.execution.commands.web.WebSelectorFinder;
import aeon.core.common.web.selectors.By;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Controller for browser.
 */
@RestController
@RequestMapping("api/v1")
public class BrowserController {

    private AutomationInfo automationInfo;
    private WebCommandExecutionFacade commandExecutionFacade;
    private Map<ObjectId, AutomationInfo> sessionTable = new HashMap<>();
    // synchronize w Collections.synchronizedMap? or ConcurrentHashMap?

    /**
     * Creates a new session.
     * @param body Body
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    @PostMapping("sessions")
    public ResponseEntity createSession(@RequestBody CreateSessionBody body) throws Exception {
        ObjectId sessionId = new ObjectId();

        BrowserHelper browserHelper = new BrowserHelper();

        automationInfo = browserHelper.setUpAutomationInfo(body);
        commandExecutionFacade = browserHelper.setUpCommandExecutionFacade(automationInfo);
        //automationInfo.setCommandExecutionFacade(commandExecutionFacade);

        sessionTable.put(sessionId, automationInfo);

        return new ResponseEntity<>(sessionId.toString(), HttpStatus.CREATED);
    }

    /**
     * Executes a given command.
     * @param sessionId Session ID
     * @param body Body
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    @PostMapping("sessions/{sessionId}/execute")
    public ResponseEntity executeCommand(@PathVariable ObjectId sessionId, @RequestBody ExecuteCommandBody body) throws Exception {
        if (body.getCommand() != null) {
            automationInfo = sessionTable.get(sessionId);

            String commandString = body.getCommand();
            List<Object> args = body.getArgs();
            List<String> byWebArgs = body.getByWebArgs();

            Class command;

            if (commandString.equals("QuitCommand") || commandString.equals("CloseCommand")) {
                command = Class.forName("aeon.core.command.execution.commands." + commandString);
                sessionTable.remove(sessionId);
            } else {
                command = Class.forName("aeon.core.command.execution.commands.web." + commandString);
            }

            Constructor[] cons = command.getConstructors();
            Class[] parameters = cons[0].getParameterTypes();
            Constructor commandCons = command.getConstructor(parameters);

            if ((parameters.length == 0) || (args != null && parameters.length == args.size())) {
                Object[] params = new Object[0];

                if (args != null) {
                    params = new Object[parameters.length];

                    for (int i = 0; i < parameters.length; i++) {
                        switch (parameters[i].getName()) {
                            case "java.lang.String":
                                params[i] = (String) args.get(i);
                                break;
                            case "java.lang.Boolean":
                                params[i] = (Boolean) args.get(i);
                                break;
                            case "boolean":
                                params[i] = (boolean) args.get(i);
                                break;
                            case "aeon.core.common.web.interfaces.IByWeb":
                                if (body.getByWebArgs().size() == 2) {
                                    String selector = body.getByWebArgs().get(0);
                                    String type = body.getByWebArgs().get(1);

                                    switch (type.toLowerCase()) {
                                        case "css":
                                            params[i] = By.cssSelector(selector);
                                            break;
                                        case "data":
                                            params[i] = By.dataAutomationAttribute(selector);
                                            break;
                                        case "da":
                                            params[i] = By.da(selector);
                                            break;
                                        case "jquery":
                                            params[i] = By.jQuery(selector);
                                            break;
                                    }
                                } else {
                                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                                }
                                break;
                            case "aeon.core.command.execution.commands.initialization.ICommandInitializer":
                                params[i] = new WebCommandInitializer(new WebControlFinder(new WebSelectorFinder()), null);
                                break;
                        }
                    }
                }

                if (CommandWithReturn.class.isAssignableFrom(command)) {
                    commandExecutionFacade.execute(automationInfo, (CommandWithReturn) commandCons.newInstance(params));

                    return new ResponseEntity<>(HttpStatus.OK);
                } else if (Command.class.isAssignableFrom(command)) {
                    commandExecutionFacade.execute(automationInfo, (Command) commandCons.newInstance(params));

                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
