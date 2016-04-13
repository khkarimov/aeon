package echo.core.common.parameters;

import echo.core.command_execution.AutomationInfo;
import echo.core.command_execution.commands.interfaces.ISelectorFinder;
import echo.core.command_execution.commands.WebElementFinder;
import echo.core.command_execution.commands.WebElementsFinder;
import echo.core.common.logging.ILog;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_interaction.ElementType;
import echo.core.test_abstraction.locators.ILocator;

import java.util.UUID;

/**
 * Class for parameters for the FrameworkAbstractionFacade.
 */
public class ParameterObject {
    private int numberOfOptions;
    private AutomationInfo automationInfo;
    private int Duration;
    private ElementType elementType;
    private WebParameters web;
    private UUID guid;
    private ILocator locator;
    private ILog log;
    private String message;
    private String alert;
    private String[] options;

    /**
     * Initializes a new instance of the <see cref="ParameterObject" /> class.
     */
    public ParameterObject() {
        web = new WebParameters();
        web.setAction(frameworkAbstractionFacade ->
        {
        });
    }

    /**
     * Initializes a new instance of the <see cref="ParameterObject" /> class.
     *
     * @param automationInfo The automation info.
     * @param elementType    The element type.
     */
    public ParameterObject(AutomationInfo automationInfo, ElementType elementType) {
        web = new WebParameters();
        this.automationInfo = automationInfo;
        this.elementType = elementType;
    }

    /**
     * Initializes a new instance of the <see cref="ParameterObject" /> class.
     *
     * @param log     The log.
     * @param message The message.
     */
    public ParameterObject(ILog log, String message) {
        web = new WebParameters();
        this.message = message;
        this.log = log;
        this.elementType = ElementType.Selenium;
    }

    /**
     * Initializes a new instance of the <see cref="ParameterObject" /> class.
     *
     * @param log             The log.
     * @param message         The log message.
     * @param switchMechanism The switch mechanism.
     * @param selector        The selector.
     * @param finder          The finder.
     */
    public ParameterObject(ILog log, String message, Iterable<IBy> switchMechanism, IBy selector, ISelectorFinder finder) {
        setGuid(UUID.randomUUID());
        web = new WebParameters();
        web.setFindIBy(selector);
        web.setSelectorFinder(finder);
        web.setSwitchMechanism(switchMechanism);
        web.setWebElementFinder(new WebElementFinder(finder));
        web.setWebElementsFinder(new WebElementsFinder(finder));
        this.elementType = ElementType.Selenium;
        this.log = log;
        this.message = message;
    }

    /**
     * Initializes a new instance of the <see cref="ParameterObject" /> class.
     *
     * @param log             The log.
     * @param message         The message.
     * @param switchMechanism The switch mechanism.
     */
    public ParameterObject(ILog log, String message, Iterable<IBy> switchMechanism) {
        web = new WebParameters();
        this.log = log;
        this.message = message;
        web.setSwitchMechanism(switchMechanism);
        web.setWebElementFinder(new WebElementFinder());
        web.setWebElementsFinder(new WebElementsFinder());
        this.elementType = ElementType.Selenium;
    }

    /**
     * Gets or sets the automation info.
     */
    public final AutomationInfo getAutomationInfo() {
        return automationInfo;
    }

    public final void setAutomationInfo(AutomationInfo value) {
        automationInfo = value;
    }

    /**
     * Gets or sets the duration.
     */
    public final int getDuration() {
        return Duration;
    }

    public final void setDuration(int value) {
        Duration = value;
    }

    /**
     * Gets or sets the element type.
     */
    public final ElementType getElementType() {
        return elementType;
    }

    public final void setElementType(ElementType value) {
        elementType = value;
    }

    /**
     * Gets or sets selenium parameters.
     */
    public final WebParameters getWeb() {
        return web;
    }

    public final void setWeb(WebParameters value) {
        web = value;
    }

    /**
     * Gets or sets the guid.
     */
    public final UUID getGuid() {
        return guid;
    }

    public final void setGuid(UUID value) {
        guid = value;
    }

    /**
     * Gets or sets locator.
     */
    public final ILocator getLocator() {
        return locator;
    }

    public final void setLocator(ILocator value) {
        locator = value;
    }

    /**
     * Gets or sets log.
     */
    public final ILog getLog() {
        return log;
    }

    public final void setLog(ILog value) {
        log = value;
    }

    /**
     * Gets or sets message.
     */
    public final String getMessage() {
        return message;
    }

    public final void setMessage(String value) {
        message = value;
    }

    /**
     * Gets or sets alert.
     */
    public final String getAlert() {
        return alert;
    }

    public final void setAlert(String value) {
        alert = value;
    }

    /**
     * Gets or sets the options.
     */
    public final String[] getOptions() {
        return options;
    }

    public final void setOptions(String[] value) {
        options = value;
    }

    /**
     * Gets or sets the numberOfOptions.
     */
    public final int getNumberOfOptions() {
        return numberOfOptions;
    }

    public final void setNumberOfOptions(int value) {
        numberOfOptions = value;
    }
}
