package com.ultimatesoftware.aeon.core.common.web;

import com.ultimatesoftware.aeon.core.common.web.selectors.ByJQuery;

/**
 * Indicates how to format a {@link ByJQuery}.
 */
public enum JQueryStringType {
    /**
     * Just the raw jquery selector.
     */
    JUST_THE_JQUERY_SELECTOR,

    /**
     * Converts the first "$(" into "find(".
     */
    AS_SUBCHAIN,

    /**
     * JavaScript code for blurring an element.
     */
    BLUR_ELEMENT,

    /**
     * JavaScript code for clicking an invisible element.
     */
    CLICK_INVISIBLE_ELEMENT,

    /**
     * JavaScript code for firing a change event on an element.
     */
    FIRE_CHANGE_EVENT,

    /**
     * JavaScript code for leaving an element.
     */
    MOUSE_OUT,

    /**
     * JavaScript code for hovering on an element.
     */
    MOUSE_OVER,

    /**
     * Returns the jquery formatted within $.makeArray().
     */
    RETURN_ELEMENT_ARRAY,

    /**
     * JavaScript code for setting an element's text.
     */
    SET_ELEMENT_TEXT,

    /**
     * JavaScript code for setting a body's text.
     */
    SET_BODY_TEXT,

    /**
     * JavaScript code for setting a div's text.
     */
    SET_DIV_TEXT,

    /**
     * JavaScript code for scrolling an element into view.
     */
    SCROLL_ELEMENT_INTO_VIEW,

    /**
     * JavaScript code for showing an element.
     */
    SHOW_ELEMENT,

    /**
     * JavaScript code for setting text in an element.
     */
    SET_MASKED_INPUT_TEXT,

    /**
     * JavaScript code getting the amount of children in an ul or ol element .
     */
    HAS_NUMBER_OF_OPTIONS,

    /**
     * JavaScript code getting the client rects of an element.
     */
    GET_CLIENT_RECTS,

    /**
     * JavaScript code getting the options of a select element.
     */
    GET_OPTIONS,

    /**
     * JavaScript code for bringing up the context menu (right click menu).
     */
    SHOW_CONTEXT_MENU,

    /**
     * JavaScript code to fire double click event on an element.
     */
    FIRE_DOUBLE_CLICK;

    /**
     * Returns a JQuery String Type based on an input value.
     * @param value the input integer.
     * @return the enum of the value.
     */
    public static JQueryStringType forValue(int value) {
        return values()[value];
    }

    /**
     * Gets the ordinal value of the {@link JQueryStringType}.
     * @return the ordinal value.
     */
    public int getValue() {
        return this.ordinal();
    }
}
