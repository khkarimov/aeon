package echo.core.common.parameters;

import com.sun.glass.ui.Size;
import echo.core.command_execution.commands.interfaces.IGridFinder;
import echo.core.command_execution.commands.interfaces.IGridSelectorFinder;
import echo.core.command_execution.commands.interfaces.ISelectorFinder;
import echo.core.command_execution.commands.interfaces.IWebElementFinder;
import echo.core.common.*;
import echo.core.common.webobjects.interfaces.IBy;
import echo.core.framework_abstraction.IDriver;
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
    private Consumer<IDriver> Action;

    public final Consumer<IDriver> getAction() {
        return Action;
    }

    public final void setAction(Consumer<IDriver> value) {
        Action = value;
    }

    /**
     * Gets or sets Args.
     */
    private Object[] Args;

    public final Object[] getArgs() {
        return Args;
    }

    public final void setArgs(Object[] value) {
        Args = value;
    }

    /**
     * Gets or sets AttributeName.
     */
    private String AttributeName;

    public final String getAttributeName() {
        return AttributeName;
    }

    public final void setAttributeName(String value) {
        AttributeName = value;
    }

    /**
     * Gets or sets ChildSelector.
     */
    private IBy ChildSelector;

    public final IBy getChildSelector() {
        return ChildSelector;
    }

    public final void setChildSelector(IBy value) {
        ChildSelector = value;
    }

    /**
     * Gets or sets the CompareType.
     */
    private CompareType compareType;

    public final CompareType getCompareType() {
        return compareType;
    }

    public final void setCompareType(CompareType value) {
        compareType = value;
    }

    /**
     * Gets or sets ComparisonOption.
     */
    private ComparisonOption comparisonOption;

    public final ComparisonOption getComparisonOption() {
        return comparisonOption;
    }

    public final void setComparisonOption(ComparisonOption value) {
        comparisonOption = value;
    }

    /**
     * Gets or sets Cookie.
     */
    private ICookie Cookie;

    public final ICookie getCookie() {
        return Cookie;
    }

    public final void setCookie(ICookie value) {
        Cookie = value;
    }

    /**
     * Gets or sets CookieName.
     */
    private String CookieName;

    public final String getCookieName() {
        return CookieName;
    }

    public final void setCookieName(String value) {
        CookieName = value;
    }

    /**
     * Gets or sets Delta.
     */
    private Duration Delta;

    public final Duration getDelta() {
        return Delta;
    }

    public final void setDelta(Duration value) {
        Delta = value;
    }

    /**
     * Gets or sets Direction.
     */
    private Direction direction;

    public final Direction getDirection() {
        return direction;
    }

    public final void setDirection(Direction value) {
        direction = value;
    }

    /**
     * Gets or sets DropElement.
     */
    private IBy DropElement;

    public final IBy getDropElement() {
        return DropElement;
    }

    public final void setDropElement(IBy value) {
        DropElement = value;
    }

    /**
     * Gets or sets DropTarget.
     */
    private IBy DropTarget;

    public final IBy getDropTarget() {
        return DropTarget;
    }

    public final void setDropTarget(IBy value) {
        DropTarget = value;
    }

    /**
     * Gets or sets Duration.
     */
    private int duration;

    public final int getDuration() {
        return duration;
    }

    public final void setDuration(int value) {
        duration = value;
    }

    /**
     * Gets or sets ExpectedDateTime.
     */
    private java.time.LocalDateTime ExpecteDateTime = java.time.LocalDateTime.MIN;

    public final java.time.LocalDateTime getExpecteDateTime() {
        return ExpecteDateTime;
    }

    public final void setExpecteDateTime(java.time.LocalDateTime value) {
        ExpecteDateTime = value;
    }

    /**
     * Gets or sets WebElementFinder.
     */
    private IWebElementFinder WebElementFinder;

    public final IWebElementFinder getWebElementFinder() {
        return WebElementFinder;
    }

    public final void setWebElementFinder(IWebElementFinder value) {
        WebElementFinder = value;
    }

    /**
     * Gets or sets WebElementsFinder.
     */
    private IWebElementsFinder WebElementsFinder;

    public final IWebElementsFinder getWebElementsFinder() {
        return WebElementsFinder;
    }

    public final void setWebElementsFinder(IWebElementsFinder value) {
        WebElementsFinder = value;
    }

    /**
     * Gets or sets FindIBy.
     */
    private IBy FindIBy;

    public final IBy getFindIBy() {
        return FindIBy;
    }

    public final void setFindIBy(IBy value) {
        FindIBy = value;
    }

    /**
     * Gets or sets Handle.
     */
    private String Handle;

    public final String getHandle() {
        return Handle;
    }

    public final void setHandle(String value) {
        Handle = value;
    }

    /**
     * Gets or sets HowManyPixelsToScroll.
     */
    private int HowManyPixelsToScroll;

    public final int getHowManyPixelsToScroll() {
        return HowManyPixelsToScroll;
    }

    public final void setHowManyPixelsToScroll(int value) {
        HowManyPixelsToScroll = value;
    }

    /**
     * Gets or sets Key.
     */
    private Keys Key;

    public final Keys getKey() {
        return Key;
    }

    public final void setKey(Keys value) {
        Key = value;
    }

    /**
     * Gets or sets KeysToSend.
     */
    private String KeysToSend;

    public final String getKeysToSend() {
        return KeysToSend;
    }

    public final void setKeysToSend(String value) {
        KeysToSend = value;
    }

    /**
     * Gets or sets Messages.
     */
    private String[] Messages;

    public final String[] getMessages() {
        return Messages;
    }

    public final void setMessages(String[] value) {
        Messages = value;
    }

    /**
     * Gets or sets OptGroup.
     */
    private String OptGroup;

    public final String getOptGroup() {
        return OptGroup;
    }

    public final void setOptGroup(String value) {
        OptGroup = value;
    }

    /**
     * Gets or sets QueryString.
     */
    private String QueryString;

    public final String getQueryString() {
        return QueryString;
    }

    public final void setQueryString(String value) {
        QueryString = value;
    }

    /**
     * Gets or sets Script.
     */
    private String Script;

    public final String getScript() {
        return Script;
    }

    public final void setScript(String value) {
        Script = value;
    }

    /**
     * Gets or sets Size.
     */
    private Size size;

    public final Size getSize() {
        return size;
    }

    public final void setSize(Size value) {
        size = value;
    }

    /**
     * Gets or sets SwitchMechanism.
     */
    private Iterable<IBy> SwitchMechanism;

    public final Iterable<IBy> getSwitchMechanism() {
        return SwitchMechanism;
    }

    public final void setSwitchMechanism(Iterable<IBy> value) {
        SwitchMechanism = value;
    }

    /**
     * Gets or sets url.
     */
    private URL url;

    public final URL getUrl() {
        return url;
    }

    public final void setUrl(URL value) {
        url = value;
    }

    /**
     * Gets or sets UrlString.
     */
    private String UrlString;

    public final String getUrlString() {
        return UrlString;
    }

    public final void setUrlString(String value) {
        UrlString = value;
    }

    /**
     * Gets or sets Value.
     */
    private String Value;

    public final String getValue() {
        return Value;
    }

    public final void setValue(String value) {
        Value = value;
    }

    /**
     * Gets or sets WebElement.
     */
    private IElement webElement;

    public final IElement getWebElement() {
        return webElement;
    }

    public final void setWebElement(IElement value) {
        webElement = value;
    }

    /**
     * Gets or sets WindowTitle.
     */
    private String WindowTitle;

    public final String getWindowTitle() {
        return WindowTitle;
    }

    public final void setWindowTitle(String value) {
        WindowTitle = value;
    }

    /**
     * Gets or sets X.
     */
    private int X;

    public final int getX() {
        return X;
    }

    public final void setX(int value) {
        X = value;
    }

    /**
     * Gets or sets X.
     */
    private int Y;

    public final int getY() {
        return Y;
    }

    public final void setY(int value) {
        Y = value;
    }

    /**
     * Gets or sets SelectOption.
     */
    private SelectOption selectOption;

    public final SelectOption getSelectOption() {
        return selectOption;
    }

    public final void setSelectOption(SelectOption value) {
        selectOption = value;
    }

    /**
     * Gets or sets SelectorFinder.
     */
    private ISelectorFinder SelectorFinder;

    public final ISelectorFinder getSelectorFinder() {
        return SelectorFinder;
    }

    public final void setSelectorFinder(ISelectorFinder value) {
        SelectorFinder = value;
    }

    /**
     * Gets or sets GridFinder.
     */
    private IGridFinder GridFinder;

    public final IGridFinder getGridFinder() {
        return GridFinder;
    }

    public final void setGridFinder(IGridFinder value) {
        GridFinder = value;
    }

    /**
     * Gets or sets GridSelectorFinder.
     */
    private IGridSelectorFinder GridSelectorFinder;

    public final IGridSelectorFinder getGridSelectorFinder() {
        return GridSelectorFinder;
    }

    public final void setGridSelectorFinder(IGridSelectorFinder value) {
        GridSelectorFinder = value;
    }

    /**
     * Gets or sets GridIndex.
     */
    private int GridIndex;

    public final int getGridIndex() {
        return GridIndex;
    }

    public final void setGridIndex(int value) {
        GridIndex = value;
    }

    /**
     * Gets or sets RowIndex.
     */
    private int RowIndex;

    public final int getRowIndex() {
        return RowIndex;
    }

    public final void setRowIndex(int value) {
        RowIndex = value;
    }

    /**
     * Gets or sets a value indicating whether WaitForAllPopupWindowsToClose.
     */
    private boolean WaitForAllPopupWindowsToClose;

    public final boolean getWaitForAllPopupWindowsToClose() {
        return WaitForAllPopupWindowsToClose;
    }

    public final void setWaitForAllPopupWindowsToClose(boolean value) {
        WaitForAllPopupWindowsToClose = value;
    }

    /**
     * Gets or sets ReferencePoint.
     */
    private IBy ReferencePoint;

    public final IBy getReferencePoint() {
        return ReferencePoint;
    }

    public final void setReferencePoint(IBy value) {
        ReferencePoint = value;
    }

    /**
     * Gets or sets ElementToMove.
     */
    private IBy ElementToMove;

    public final IBy getElementToMove() {
        return ElementToMove;
    }

    public final void setElementToMove(IBy value) {
        ElementToMove = value;
    }
}