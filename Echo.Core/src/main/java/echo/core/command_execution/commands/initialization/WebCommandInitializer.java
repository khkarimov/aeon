package echo.core.command_execution.commands.initialization;

import echo.core.common.parameters.ParameterObject;
import echo.core.common.parameters.WebParameters;
import echo.core.common.webobjects.interfaces.IBy;

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
     * @param frameworkAbstractionFacade Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    public final void FindElement(IFrameworkAbstractionFacade frameworkAbstractionFacade, ParameterObject parameterObject) {
        IElement IElement = parameterObject.getWeb().getWebElementFinder().FindElement(frameworkAbstractionFacade, parameterObject);
        parameterObject.getWeb().setWebElement(webElement);
        frameworkAbstractionFacade.ScrollElementIntoView(parameterObject);
    }

    /**
     * Finds the web element and gives the reference to the Parameter Object.
     *
     * @param frameworkAbstractionFacade Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    public final void FindElementDoNotScroll(IFrameworkAbstractionFacade frameworkAbstractionFacade, ParameterObject parameterObject) {
        IElement IElement = parameterObject.getWeb().getWebElementFinder().FindElement(frameworkAbstractionFacade, parameterObject);
        parameterObject.getWeb().setWebElement(webElement);
    }

    /**
     * Finds the selector.
     *
     * @param frameworkAbstractionFacade Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    public final void FindSelector(IFrameworkAbstractionFacade frameworkAbstractionFacade, ParameterObject parameterObject) {
        parameterObject.getWeb().setFindIBy(
                parameterObject.getWeb().getSelectorFinder()
                        .FindSelector(frameworkAbstractionFacade, parameterObject.getWeb().getFindIBy()));

        parameterObject.getWeb().setWebElement(frameworkAbstractionFacade.FindIElement(parameterObject));
    }

    /**
     * Gets the command action.
     *
     * @param parameterObject Parameter Object.
     * @return The command action.
     */
    public final Consumer<IFrameworkAbstractionFacade> GetCommandAction(ParameterObject parameterObject) {
        Consumer<IFrameworkAbstractionFacade> action = frameworkAbstractionFacade ->
        {
            if (parameterObject.getWeb().getSwitchMechanism() != null) {
                IBy current = parameterObject.getWeb().getFindIBy();
                frameworkAbstractionFacade.SwitchToDefaultContent();
                frameworkAbstractionFacade.FocusWindow();

                for (IBy by : parameterObject.getWeb().getSwitchMechanism()) {
                    parameterObject.getWeb().setFindIBy(by);
                    frameworkAbstractionFacade.SwitchToFrame(parameterObject);
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
    public final Function<IFrameworkAbstractionFacade, Object> GetCommandFunc(ParameterObject parameterObject) {
        Function<IFrameworkAbstractionFacade, Object> func = frameworkAbstractionFacade ->
        {
            if (parameterObject.getWeb().getSwitchMechanism() != null) {
                IBy current = parameterObject.getWeb().getFindIBy();

                frameworkAbstractionFacade.SwitchToDefaultContent();

                for (IBy by : parameterObject.getWeb().getSwitchMechanism()) {
                    parameterObject.getWeb().setFindIBy(by);
                    frameworkAbstractionFacade.SwitchToFrame(parameterObject);
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
     * @param frameworkAbstractionFacade Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    public final void GetGridIndex(IFrameworkAbstractionFacade frameworkAbstractionFacade, ParameterObject parameterObject) {
        parameterObject.getWeb().setGridIndex(parameterObject.getWeb().getGridFinder().GetGridIndex(frameworkAbstractionFacade));
    }

    /**
     * Finds the Row Index.
     *
     * @param frameworkAbstractionFacade Framework Abstraction Facade.
     * @param parameterObject            Parameter Object.
     */
    public final void GetRowIndex(IFrameworkAbstractionFacade frameworkAbstractionFacade, ParameterObject parameterObject) {
        parameterObject.getWeb().setRowIndex(parameterObject.getWeb().getGridSelectorFinder().GetRowIndex(frameworkAbstractionFacade));
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