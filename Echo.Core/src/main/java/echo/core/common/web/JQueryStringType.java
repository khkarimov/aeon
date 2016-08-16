package echo.core.common.web;

/**
 * Indicates how to format a <see cref="ByJQuery"/>.
 */
public enum JQueryStringType {
    /**
     * Just the raw jquery selector.
     */
    JustTheJQuerySelector,

    /**
     * Converts the first "$(" into "find(".
     */
    AsSubchain,

    /**
     * JavaScript code for blurring an element.
     */
    BlurElement,

    /**
     * JavaScript code for clicking an invisible element.
     */
    ClickInvisibleElement,

    /**
     * JavaScript code for firing a change event on an element.
     */
    FireChangeEvent,

    /**
     * JavaScript code for leaving an element.
     */
    MouseOut,

    /**
     * JavaScript code for hovering on an element.
     */
    MouseOver,

    /**
     * Returns the jquery formatted within $.makeArray().
     */
    ReturnElementArray,

    /**
     * JavaScript code for setting an element's text.
     */
    SetElementText,

    /**
     * JavaScript code for setting a body's text.
     */
    SetBodyText,

    /**
     * JavaScript code for setting a div's text.
     */
    SetDivText,

    /**
     * JavaScript code for setting a value's text.
     */
    SetValueText,

    /**
     * JavaScript code for scrolling an element into view.
     */
    ScrollElementIntoView,

    /**
     * JavaScript code for showing an element.
     */
    ShowElement,

    /**
     * JavaScript code for setting text in an element.
     */
    SetMaskedInputText,

    /**
     * JavaScript code getting the amount of children in an ul or ol element .
     */
    HasNumberOfOptions,

    /**
     * JavaScript code getting the client rects of an element.
     */
    GetClientRects,

    /**
     * JavaScript code getting the options of a select element
     */
    GetOptions;

    public static JQueryStringType forValue(int value) {
        return values()[value];
    }

    public int getValue() {
        return this.ordinal();
    }
}
