package echo.core.common.parameters;

import com.sun.glass.ui.Size;
import echo.core.command_execution.commands.interfaces.IGridFinder;
import echo.core.command_execution.commands.interfaces.IGridSelectorFinder;
import echo.core.command_execution.commands.interfaces.ISelectorFinder;
import echo.core.command_execution.commands.interfaces.IWebElementFinder;
import echo.core.common.*;
import echo.core.common.web.IWebElementsFinder;
import echo.core.common.web.SelectOption;
import echo.core.common.web.interfaces.IBy;
import echo.core.framework_abstraction.IWebDriver;
import echo.core.framework_abstraction.IElement;
import echo.core.framework_abstraction.ICookie;
import org.openqa.selenium.Keys;

import java.net.URL;
import java.time.Duration;
import java.util.function.Consumer;

/**
 * Class for parameters for the driver.
 */
public class WebParameters {
    /**
     * Gets or sets Action.
     */
    private Consumer<IWebDriver> Action;
    /**
     * Gets or sets Args.
     */
    private Object[] Args;
    /**
     * Gets or sets AttributeName.
     */
    private String AttributeName;
    /**
     * Gets or sets ChildSelector.
     */
    private IBy ChildSelector;
    /**
     * Gets or sets the CompareType.
     */
    private CompareType compareType;
    /**
     * Gets or sets ComparisonOption.
     */
    private ComparisonOption comparisonOption;
    /**
     * Gets or sets Cookie.
     */
    private ICookie Cookie;
    /**
     * Gets or sets CookieName.
     */
    private String CookieName;
    /**
     * Gets or sets Delta.
     */
    private Duration Delta;
    /**
     * Gets or sets Direction.
     */
    private Direction direction;
    /**
     * Gets or sets DropElement.
     */
    private IBy DropElement;
    /**
     * Gets or sets DropTarget.
     */
    private IBy DropTarget;
    /**
     * Gets or sets Duration.
     */
    private int duration;
    /**
     * Gets or sets ExpectedDateTime.
     */
    private java.time.LocalDateTime ExpecteDateTime = java.time.LocalDateTime.MIN;
    /**
     * Gets or sets WebElementFinder.
     */
    private IWebElementFinder WebElementFinder;
    /**
     * Gets or sets WebElementsFinder.
     */
    private IWebElementsFinder WebElementsFinder;
    /**
     * Gets or sets FindIBy.
     */
    private IBy FindIBy;
    /**
     * Gets or sets Handle.
     */
    private String Handle;
    /**
     * Gets or sets HowManyPixelsToScroll.
     */
    private int HowManyPixelsToScroll;
    /**
     * Gets or sets Key.
     */
    private Keys Key;
    /**
     * Gets or sets KeysToSend.
     */
    private String KeysToSend;
    /**
     * Gets or sets Messages.
     */
    private String[] Messages;
    /**
     * Gets or sets OptGroup.
     */
    private String OptGroup;
    /**
     * Gets or sets QueryString.
     */
    private String QueryString;
    /**
     * Gets or sets Script.
     */
    private String Script;
    /**
     * Gets or sets Size.
     */
    private Size size;
    /**
     * Gets or sets SwitchMechanism.
     */
    private Iterable<IBy> SwitchMechanism;
    /**
     * Gets or sets url.
     */
    private URL url;
    /**
     * Gets or sets UrlString.
     */
    private String UrlString;
    /**
     * Gets or sets Value.
     */
    private String Value;
    /**
     * Gets or sets WebElement.
     */
    private IElement webElement;
    /**
     * Gets or sets WindowTitle.
     */
    private String WindowTitle;
    /**
     * Gets or sets X.
     */
    private int X;
    /**
     * Gets or sets X.
     */
    private int Y;
    /**
     * Gets or sets SelectOption.
     */
    private SelectOption selectOption;
    /**
     * Gets or sets SelectorFinder.
     */
    private ISelectorFinder SelectorFinder;
    /**
     * Gets or sets GridFinder.
     */
    private IGridFinder GridFinder;
    /**
     * Gets or sets GridSelectorFinder.
     */
    private IGridSelectorFinder GridSelectorFinder;
    /**
     * Gets or sets GridIndex.
     */
    private int GridIndex;
    /**
     * Gets or sets RowIndex.
     */
    private int RowIndex;
    /**
     * Gets or sets a value indicating whether WaitForAllPopupWindowsToClose.
     */
    private boolean WaitForAllPopupWindowsToClose;
    /**
     * Gets or sets ReferencePoint.
     */
    private IBy ReferencePoint;
    /**
     * Gets or sets ElementToMove.
     */
    private IBy ElementToMove;

    public final Consumer<IWebDriver> getAction() {
        return Action;
    }

    public final void setAction(Consumer<IWebDriver> value) {
        Action = value;
    }

    public final Object[] getArgs() {
        return Args;
    }

    public final void setArgs(Object[] value) {
        Args = value;
    }

    public final String getAttributeName() {
        return AttributeName;
    }

    public final void setAttributeName(String value) {
        AttributeName = value;
    }

    public final IBy getChildSelector() {
        return ChildSelector;
    }

    public final void setChildSelector(IBy value) {
        ChildSelector = value;
    }

    public final CompareType getCompareType() {
        return compareType;
    }

    public final void setCompareType(CompareType value) {
        compareType = value;
    }

    public final ComparisonOption getComparisonOption() {
        return comparisonOption;
    }

    public final void setComparisonOption(ComparisonOption value) {
        comparisonOption = value;
    }

    public final ICookie getCookie() {
        return Cookie;
    }

    public final void setCookie(ICookie value) {
        Cookie = value;
    }

    public final String getCookieName() {
        return CookieName;
    }

    public final void setCookieName(String value) {
        CookieName = value;
    }

    public final Duration getDelta() {
        return Delta;
    }

    public final void setDelta(Duration value) {
        Delta = value;
    }

    public final Direction getDirection() {
        return direction;
    }

    public final void setDirection(Direction value) {
        direction = value;
    }

    public final IBy getDropElement() {
        return DropElement;
    }

    public final void setDropElement(IBy value) {
        DropElement = value;
    }

    public final IBy getDropTarget() {
        return DropTarget;
    }

    public final void setDropTarget(IBy value) {
        DropTarget = value;
    }

    public final int getDuration() {
        return duration;
    }

    public final void setDuration(int value) {
        duration = value;
    }

    public final java.time.LocalDateTime getExpecteDateTime() {
        return ExpecteDateTime;
    }

    public final void setExpecteDateTime(java.time.LocalDateTime value) {
        ExpecteDateTime = value;
    }

    public final IWebElementFinder getWebElementFinder() {
        return WebElementFinder;
    }

    public final void setWebElementFinder(IWebElementFinder value) {
        WebElementFinder = value;
    }

    public final IWebElementsFinder getWebElementsFinder() {
        return WebElementsFinder;
    }

    public final void setWebElementsFinder(IWebElementsFinder value) {
        WebElementsFinder = value;
    }

    public final IBy getFindIBy() {
        return FindIBy;
    }

    public final void setFindIBy(IBy value) {
        FindIBy = value;
    }

    public final String getHandle() {
        return Handle;
    }

    public final void setHandle(String value) {
        Handle = value;
    }

    public final int getHowManyPixelsToScroll() {
        return HowManyPixelsToScroll;
    }

    public final void setHowManyPixelsToScroll(int value) {
        HowManyPixelsToScroll = value;
    }

    public final Keys getKey() {
        return Key;
    }

    public final void setKey(Keys value) {
        Key = value;
    }

    public final String getKeysToSend() {
        return KeysToSend;
    }

    public final void setKeysToSend(String value) {
        KeysToSend = value;
    }

    public final String[] getMessages() {
        return Messages;
    }

    public final void setMessages(String[] value) {
        Messages = value;
    }

    public final String getOptGroup() {
        return OptGroup;
    }

    public final void setOptGroup(String value) {
        OptGroup = value;
    }

    public final String getQueryString() {
        return QueryString;
    }

    public final void setQueryString(String value) {
        QueryString = value;
    }

    public final String getScript() {
        return Script;
    }

    public final void setScript(String value) {
        Script = value;
    }

    public final Size getSize() {
        return size;
    }

    public final void setSize(Size value) {
        size = value;
    }

    public final Iterable<IBy> getSwitchMechanism() {
        return SwitchMechanism;
    }

    public final void setSwitchMechanism(Iterable<IBy> value) {
        SwitchMechanism = value;
    }

    public final URL getUrl() {
        return url;
    }

    public final void setUrl(URL value) {
        url = value;
    }

    public final String getUrlString() {
        return UrlString;
    }

    public final void setUrlString(String value) {
        UrlString = value;
    }

    public final String getValue() {
        return Value;
    }

    public final void setValue(String value) {
        Value = value;
    }

    public final IElement getWebElement() {
        return webElement;
    }

    public final void setWebElement(IElement value) {
        webElement = value;
    }

    public final String getWindowTitle() {
        return WindowTitle;
    }

    public final void setWindowTitle(String value) {
        WindowTitle = value;
    }

    public final int getX() {
        return X;
    }

    public final void setX(int value) {
        X = value;
    }

    public final int getY() {
        return Y;
    }

    public final void setY(int value) {
        Y = value;
    }

    public final SelectOption getSelectOption() {
        return selectOption;
    }

    public final void setSelectOption(SelectOption value) {
        selectOption = value;
    }

    public final ISelectorFinder getSelectorFinder() {
        return SelectorFinder;
    }

    public final void setSelectorFinder(ISelectorFinder value) {
        SelectorFinder = value;
    }

    public final IGridFinder getGridFinder() {
        return GridFinder;
    }

    public final void setGridFinder(IGridFinder value) {
        GridFinder = value;
    }

    public final IGridSelectorFinder getGridSelectorFinder() {
        return GridSelectorFinder;
    }

    public final void setGridSelectorFinder(IGridSelectorFinder value) {
        GridSelectorFinder = value;
    }

    public final int getGridIndex() {
        return GridIndex;
    }

    public final void setGridIndex(int value) {
        GridIndex = value;
    }

    public final int getRowIndex() {
        return RowIndex;
    }

    public final void setRowIndex(int value) {
        RowIndex = value;
    }

    public final boolean getWaitForAllPopupWindowsToClose() {
        return WaitForAllPopupWindowsToClose;
    }

    public final void setWaitForAllPopupWindowsToClose(boolean value) {
        WaitForAllPopupWindowsToClose = value;
    }

    public final IBy getReferencePoint() {
        return ReferencePoint;
    }

    public final void setReferencePoint(IBy value) {
        ReferencePoint = value;
    }

    public final IBy getElementToMove() {
        return ElementToMove;
    }

    public final void setElementToMove(IBy value) {
        ElementToMove = value;
    }
}