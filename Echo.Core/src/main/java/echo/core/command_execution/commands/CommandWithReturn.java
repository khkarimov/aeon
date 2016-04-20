package echo.core.command_execution.commands;

import echo.core.command_execution.commands.initialization.ICommandInitializer;
import echo.core.command_execution.commands.initialization.WebCommandInitializer;
import echo.core.command_execution.commands.interfaces.ICommand;
import echo.core.common.Resources;
import echo.core.common.logging.ILog;
import echo.core.common.parameters.ParameterObject;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.IWebDriver;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.function.Function;

/**
 * A command with a return value.
 */
public abstract class CommandWithReturn implements ICommand<Function<IWebDriver, Object>> {
    /**
     * Initializes a new instance of the <see cref="CommandWithReturn"/> class.
     *
     * @param log     The logger.
     * @param message The message to log.
     */
    protected CommandWithReturn(ILog log, String message) {
        this(log, message, null);
        ////throw new NotImplementedException("CommandWithReturn should not be called with this constructor.");
    }

    /**
     * Initializes a new instance of the <see cref="CommandWithReturn"/> class.
     *
     * @param log             The log.
     * @param message         The message to log.
     * @param switchMechanism The switch mechanism.
     */
    protected CommandWithReturn(ILog log, String message, Iterable<IBy> switchMechanism) {
        if (log == null) {
            throw new IllegalArgumentException("log");
        }

        if (StringUtils.isEmpty(message)) {
            message = this.getClass().getSimpleName();
        }

        setParameterObject(new ParameterObject(log, message));
        setCommandInitializer(new WebCommandInitializer());
        getParameterObject().getWeb().setSwitchMechanism(switchMechanism);
        getParameterObject().setGuid(UUID.randomUUID());
        log.Info(getParameterObject().getGuid(), String.format(Resources.getString("CommandInstantiated_Info"), message));
    }

    /**
     * Initializes a new instance of the <see cref="CommandWithReturn"/> class.
     *
     * @param parameterObject     The framework param object.
     * @param commandInitiazlizer The command initializer.
     */
    protected CommandWithReturn(ParameterObject parameterObject, ICommandInitializer commandInitiazlizer) {
        setParameterObject(parameterObject);
        if (getParameterObject().getLog() == null) {
            throw new IllegalArgumentException("log");
        }

        if (StringUtils.isEmpty(getParameterObject().getMessage())) {
            getParameterObject().setMessage(this.getClass().getSimpleName());
        }

        setCommandInitializer(commandInitiazlizer);
        getParameterObject().setGuid(UUID.randomUUID());
        getParameterObject().getLog().Info(getParameterObject().getGuid(),
                String.format(Resources.getString("CommandInstantiated_Info"), getParameterObject().getMessage()));
    }

    /**
     * Gets or sets ParameterObject.
     */
    private ParameterObject parameterObject;

    public final ParameterObject getParameterObject() {
        return parameterObject;
    }

    public final void setParameterObject(ParameterObject value) {
        parameterObject = value;
    }

    /**
     * Gets or sets the command initializer.
     */
    private ICommandInitializer CommandInitializer;

    public final ICommandInitializer getCommandInitializer() {
        return CommandInitializer;
    }

    public final void setCommandInitializer(ICommandInitializer value) {
        CommandInitializer = value;
    }

    /**
     * Gets the GUID for the command.
     */
    public final UUID getGuid() {
        return getParameterObject().getGuid();
    }

    /**
     * Gets the delegate for the command.
     *
     * @return A delegate.
     */
    public final Function<IWebDriver, Object> GetCommandDelegate() {
        Function<IWebDriver, Object> func;

        func = driver ->
        {
            getCommandInitializer()
                    .GetCommandFunc(getParameterObject())
                    .apply(driver);

            if (getCommandInitializer().ResetParameterObject() != null) {
                setParameterObject(getCommandInitializer().ResetParameterObject());
            }

            getCommandInitializer().SaveParameterObject(getParameterObject());

            return CommandDelegate(driver);
        };

        return func;
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param driver The framework abstraction facade.
     * @return The return value for the command.
     */
    protected abstract Object CommandDelegate(IWebDriver driver);
}