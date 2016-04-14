package echo.core.command_execution.commands.initialization;

import echo.core.common.parameters.ParameterObject;
import echo.core.common.parameters.WebParameters;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.IDriver;
import echo.core.framework_abstraction.IElement;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Initializes the command.
 */
public class WebCommandInitializer implements ICommandInitializer {

    private ParameterObject originalParameterObject;

    /**
     * Finds the web element and gives the reference to the Parameter Object.
     *
     * @param driver Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    public final void FindElement(IDriver driver, ParameterObject parameterObject) {
        IElement element = parameterObject.getWeb().getWebElementFinder().FindElement(driver, parameterObject);
        parameterObject.getWeb().setWebElement(element);
        driver.ScrollElementIntoView(parameterObject);
    }

    /**
     * Finds the web element and gives the reference to the Parameter Object.
     *
     * @param driver Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    public final void FindElementDoNotScroll(IDriver driver, ParameterObject parameterObject) {
        IElement element = parameterObject.getWeb().getWebElementFinder().FindElement(driver, parameterObject);
        parameterObject.getWeb().setWebElement(element);
    }

    /**
     * Finds the selector.
     *
     * @param driver Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    public final void FindSelector(IDriver driver, ParameterObject parameterObject) {
        parameterObject.getWeb().setFindIBy(
                parameterObject.getWeb().getSelectorFinder()
                        .FindSelector(driver, parameterObject.getWeb().getFindIBy()));

        parameterObject.getWeb().setWebElement(driver.FindElement(parameterObject));
    }

    /**
     * Gets the command action.
     *
     * @param parameterObject Parameter Object.
     * @return The command action.
     */
    public final Consumer<IDriver> GetCommandAction(ParameterObject parameterObject) {
        Consumer<IDriver> action = driver ->
        {
            if (parameterObject.getWeb().getSwitchMechanism() != null) {
                IBy current = parameterObject.getWeb().getFindIBy();
                driver.SwitchToDefaultContent(parameterObject);
                driver.FocusWindow(parameterObject);

                for (IBy by : parameterObject.getWeb().getSwitchMechanism()) {
                    parameterObject.getWeb().setFindIBy(by);
                    driver.SwitchToFrame(parameterObject);
                }

                parameterObject.getWeb().setFindIBy(current);
            }
        };

        return action;
    }

    /**
     * Gets the command function.
     *
     * @param parameterObject Parameter Object.
     * @return The command function.
     */
    public final Function<IDriver, Object> GetCommandFunc(ParameterObject parameterObject) {
        Function<IDriver, Object> func = driver ->
        {
            if (parameterObject.getWeb().getSwitchMechanism() != null) {
                IBy current = parameterObject.getWeb().getFindIBy();

                driver.SwitchToDefaultContent(parameterObject);

                for (IBy by : parameterObject.getWeb().getSwitchMechanism()) {
                    parameterObject.getWeb().setFindIBy(by);
                    driver.SwitchToFrame(parameterObject);
                }

                parameterObject.getWeb().setFindIBy(current);
            }

            return null;
        };

        return func;
    }

    /**
     * Finds the Grid Index.
     *
     * @param driver Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    public final void GetGridIndex(IDriver driver, ParameterObject parameterObject) {
        parameterObject.getWeb().setGridIndex(parameterObject.getWeb().getGridFinder().GetGridIndex(driver));
    }

    /**
     * Finds the Row Index.
     *
     * @param driver Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    public final void GetRowIndex(IDriver driver, ParameterObject parameterObject) {
        parameterObject.getWeb().setRowIndex(parameterObject.getWeb().getGridSelectorFinder().GetRowIndex(driver));
    }

    /**
     * Saves the parameter object.
     *
     * @param parameterObject Parameter Object.
     */
    public final void SaveParameterObject(ParameterObject parameterObject) {
        originalParameterObject = new ParameterObject();
        originalParameterObject.setWeb(new WebParameters());

        originalParameterObject.setAlert(parameterObject.getAlert());
        originalParameterObject.setAutomationInfo(parameterObject.getAutomationInfo());
        originalParameterObject.setDuration(parameterObject.getDuration());
        originalParameterObject.setElementType(parameterObject.getElementType());
        originalParameterObject.setGuid(parameterObject.getGuid());
        originalParameterObject.setLocator(parameterObject.getLocator());
        originalParameterObject.setLog(parameterObject.getLog());
        originalParameterObject.setMessage(parameterObject.getMessage());
        originalParameterObject.setNumberOfOptions(parameterObject.getNumberOfOptions());
        originalParameterObject.setOptions(parameterObject.getOptions());

        WebParameters web = originalParameterObject.getWeb();
        web.setAction(parameterObject.getWeb().getAction());
        web.setArgs(parameterObject.getWeb().getArgs());
        web.setAttributeName(parameterObject.getWeb().getAttributeName());
        web.setChildSelector(parameterObject.getWeb().getChildSelector());
        web.setCompareType(parameterObject.getWeb().getCompareType());
        web.setComparisonOption(parameterObject.getWeb().getComparisonOption());
        web.setCookie(parameterObject.getWeb().getCookie());
        web.setCookieName(parameterObject.getWeb().getCookieName());
        web.setDelta(parameterObject.getWeb().getDelta());
        web.setDirection(parameterObject.getWeb().getDirection());
        web.setDropElement(parameterObject.getWeb().getDropElement());
        web.setDropTarget(parameterObject.getWeb().getDropTarget());
        web.setDuration(parameterObject.getWeb().getDuration());
        web.setElementToMove(parameterObject.getWeb().getElementToMove());
        web.setExpecteDateTime(parameterObject.getWeb().getExpecteDateTime());
        web.setFindIBy(parameterObject.getWeb().getFindIBy());
        web.setGridFinder(parameterObject.getWeb().getGridFinder());
        web.setGridIndex(parameterObject.getWeb().getGridIndex());
        web.setGridSelectorFinder(parameterObject.getWeb().getGridSelectorFinder());
        web.setHandle(parameterObject.getWeb().getHandle());
        web.setHowManyPixelsToScroll(parameterObject.getWeb().getHowManyPixelsToScroll());
        web.setKey(parameterObject.getWeb().getKey());
        web.setKeysToSend(parameterObject.getWeb().getKeysToSend());
        web.setMessages(parameterObject.getWeb().getMessages());
        web.setOptGroup(parameterObject.getWeb().getOptGroup());
        web.setQueryString(parameterObject.getWeb().getQueryString());
        web.setReferencePoint(parameterObject.getWeb().getReferencePoint());
        web.setRowIndex(parameterObject.getWeb().getRowIndex());
        web.setScript(parameterObject.getWeb().getScript());
        web.setSelectOption(parameterObject.getWeb().getSelectOption());
        web.setSelectorFinder(parameterObject.getWeb().getSelectorFinder());
        web.setSize(parameterObject.getWeb().getSize());
        web.setSwitchMechanism(parameterObject.getWeb().getSwitchMechanism());
        web.setUrl(parameterObject.getWeb().getUrl());
        web.setUrlString(parameterObject.getWeb().getUrlString());
        web.setValue(parameterObject.getWeb().getValue());
        web.setWaitForAllPopupWindowsToClose(parameterObject.getWeb().getWaitForAllPopupWindowsToClose());
        web.setWebElement(parameterObject.getWeb().getWebElement());
        web.setWebElementFinder(parameterObject.getWeb().getWebElementFinder());
        web.setWebElementsFinder(parameterObject.getWeb().getWebElementsFinder());
        web.setWindowTitle(parameterObject.getWeb().getWindowTitle());
        web.setX(parameterObject.getWeb().getX());
        web.setY(parameterObject.getWeb().getY());
    }

    /**
     * Resets the parameter object.
     *
     * @return The new parameter object.
     */
    public final ParameterObject ResetParameterObject() {
        return originalParameterObject;
    }
}