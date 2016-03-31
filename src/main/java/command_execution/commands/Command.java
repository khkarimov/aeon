package command_execution.commands;

import command_execution.commands.initialization.ICommandInitializer;
import command_execution.commands.initialization.WebCommandInitializer;
import command_execution.commands.interfaces.ICommand;
import common.Resources;
import common.logging.ILog;
import common.parameters.ParameterObject;
import common.webobjects.interfaces.IBy;
import framework_abstraction.IFrameworkAbstractionFacade;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * A command.
 */
public abstract class Command implements ICommand<Consumer<IFrameworkAbstractionFacade>> {

    private ParameterObject parameterObject;
    private ICommandInitializer commandInitializer;

    /**
     * Initializes a new instance of the <see cref="Command"/> class.
     *
     * @param log     The logger.
     * @param message The message to log.
     */
    protected Command(ILog log, String message) {
        this(log, message, null);
    }

    /**
     * Initializes a new instance of the <see cref="Command"/> class.
     *
     * @param parameterObject    The framework param object.
     * @param commandInitializer The command initializer.
     */
    protected Command(ParameterObject parameterObject, ICommandInitializer commandInitializer) {
        this.parameterObject = parameterObject;
        this.commandInitializer = commandInitializer;

        if (parameterObject.getLog() == null) {
            throw new IllegalArgumentException("log");
        }

        if (StringUtils.isEmpty(parameterObject.getMessage())) {
            getParameterObject().setMessage(this.getClass().getSimpleName());
        }

        parameterObject.setGuid(UUID.randomUUID());
        parameterObject.getLog().Info(
                getParameterObject().getGuid(),
                String.format(Resources.getString("CommandInstantiated_Info"), parameterObject.getMessage()));
    }

    /**
     * Initializes a new instance of the <see cref="Command"/> class.
     *
     * @param log             The log.
     * @param message         The message to log.
     * @param switchMechanism The switch mechanism.
     */
    protected Command(ILog log, String message, Iterable<IBy> switchMechanism) {
        if (log == null) {
            throw new IllegalArgumentException("log");
        }

        if (StringUtils.isEmpty(parameterObject.getMessage())) {
            message = this.getClass().getSimpleName();
        }

        // This will only be called by selenium commands.
        ParameterObject parameterObject = new ParameterObject(log, message);
        setParameterObject(parameterObject);
        setCommandInitializer(new WebCommandInitializer());
        parameterObject.getWeb().setSwitchMechanism(switchMechanism);
        parameterObject.setGuid(UUID.randomUUID());
        parameterObject.getLog().Info(
                getParameterObject().getGuid(),
                String.format(Resources.getString("CommandInstantiated_Info"), parameterObject.getMessage()));
    }

    /**
     * Gets or sets ParameterObject.
     */
    public final ParameterObject getParameterObject() {
        return parameterObject;
    }

    public final void setParameterObject(ParameterObject value) {
        parameterObject = value;
    }

    /**
     * Gets or sets the command initializer.
     */
    public final ICommandInitializer getCommandInitializer() {
        return commandInitializer;
    }

    public final void setCommandInitializer(ICommandInitializer value) {
        commandInitializer = value;
    }

    /**
     * Gets the GUID for the command.
     */
    public final UUID getGuid() {
        return getParameterObject().getGuid();
    }

    /**
     * Gets the delegate for the command. It also defines the frame switching mechanism. Overriding this property is meant for custom commands only.
     * <p>
     * <p>
     * <see cref="GetCommandDelegate"/> returns this property. This design allows internal child classes to override the frame switching mechanism while still protecting the mechanism from public overrides.
     * It is not intended for end-users to override this property; thus, it should be sealed at the end of the inheritance chain.
     */
    public Consumer<IFrameworkAbstractionFacade> getCmdDelegateProperty() {
        Consumer<IFrameworkAbstractionFacade> action = frameworkAbstractionFacade ->
        {
        };

        action.andThen(getCommandInitializer().GetCommandAction(getParameterObject()));

        action.andThen(frameworkAbstractionFacade ->
        {
            if (getCommandInitializer().ResetParameterObject() != null) {
                setParameterObject(getCommandInitializer().ResetParameterObject());
            }
        });

        action.andThen(frameworkAbstractionFacade -> getCommandInitializer().SaveParameterObject(getParameterObject()));
        action.andThen(frameworkAbstractionFacade -> CommandDelegate(frameworkAbstractionFacade));
        return action;
    }

    /**
     * Gets the delegate for the command.
     * <p>
     * <p>
     * GetCommandDelegate is a wrapper for the delegate and actual command.
     * The internal virtual CmdDelegateProperty holds the logic for the delegate.
     * In this way, the logic can be used by an outside class, but only modified by internal classes.
     * This is intentionally not virtual.
     *
     * @return The delegate property (<see cref="CmdDelegateProperty"/>).
     */
    public final Consumer<IFrameworkAbstractionFacade> GetCommandDelegate() {
        return getCmdDelegateProperty();
    }

    /**
     * The method which provides the logic for the command.
     *
     * @param frameworkAbstractionFacade The framework abstraction facade.
     */
    protected abstract void CommandDelegate(IFrameworkAbstractionFacade frameworkAbstractionFacade);
}