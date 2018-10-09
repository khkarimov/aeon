package browser;

import aeon.core.command.execution.AutomationInfo;
import aeon.core.command.execution.WebCommandExecutionFacade;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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

    private BrowserHelper browserHelper;
    private CommandHelper commandHelper;

    /**
     * Constructs Browser Controller.
     * @param browserHelper Browser helper class
     * @param commandHelper Command helper class
     */
    @Autowired
    public BrowserController(BrowserHelper browserHelper, CommandHelper commandHelper) {
        this.browserHelper = browserHelper;
        this.commandHelper = commandHelper;
    }

    /**
     * Sets the session table.
     * @param sessionTable Session table
     */
    public void setSessionTable(Map<ObjectId, AutomationInfo> sessionTable) {
        this.sessionTable = sessionTable;
    }

    /**
     * Creates a new session.
     * @param body Body
     * @return Response entity
     * @throws Exception Throws an exception if an error occurs
     */
    @PostMapping("sessions")
    public ResponseEntity createSession(@RequestBody CreateSessionBody body) throws Exception {
        ObjectId sessionId = browserHelper.createSessionId();

        automationInfo = browserHelper.setUpAutomationInfo(body);
        commandExecutionFacade = browserHelper.setUpCommandExecutionFacade(automationInfo);

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

            Constructor commandCons = commandHelper.createConstructor(commandString);

            if (commandCons.getName().equals("aeon.core.command.execution.commands." + commandString)) {
                sessionTable.remove(sessionId);
            }

            try {
                return commandHelper.executeCommand(commandCons, body, automationInfo, commandExecutionFacade);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
