package com.ultimatesoftware.aeon.extensions.selenium.jquery;

/**
 * Wraps JavaScript within a code to inject jquery.
 */
public class InjectJQueryScripter extends JavaScriptFinalizer {
    /* JavaScript to be wrapped around JQuery strings
     *
     * var aeonCallback = arguments[arguments.length-1];
     * var aeonNonCallbackArguments = (arguments.length == 1) ? [] : Array.prototype.slice.call(arguments, 0, arguments.length-2);
     * function aeonFunction() {
     *     // insert code here
     * }
     * var aeonJQueryLoaded = false;
     * function aeonOnFinishJQueryLoading() {
     *     if (aeonJQueryLoaded) return;
     *     aeonJQueryLoaded = true;
     *     var $ = window.jquery;
     *     aeonCallback(aeonFunction(aeonNonCallbackArguments));
     * }
     * if(!window.jquery) {
     *     var script = document.createElement('script');
     *     script.type = 'text/javascript';
     *     script.src = 'https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js';
     *     script.onreadystatechange= function () { if (this.readyState == 'loaded' || this.readyState == 'complete') aeonOnFinishJQueryLoading(); };
     *     script.onload = aeonOnFinishJQueryLoading;
     *     document.getElementsByTagName('head')[0].appendChild(script);
     * } else
     *     aeonCallback(aeonFunction(aeonNonCallbackArguments));
     */
    private static final String NEED_JQUERY_SCRIPT_BEFORE = "var aeonCallback=arguments[arguments.length-1];var aeonNonCallbackArguments=arguments.length==1?[]:Array.prototype.slice.call(arguments,0,arguments.length-2);function aeonFunction(){";
    private static final String NEED_JQUERY_SCRIPT_AFTER = "}var aeonJQueryLoaded=false;function aeonOnFinishJQueryLoading(){if(aeonJQueryLoaded)return;aeonJQueryLoaded=true;var $=window.jquery;aeonCallback(aeonFunction(aeonNonCallbackArguments))}" +
            "if(!window.jquery){var script=document.createElement('script');script.type='text/javascript';script.src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js';script.onreadystatechange=function(){if(this.readyState=='loaded'||this.readyState=='complete')" +
            "aeonOnFinishJQueryLoading()};script.onload=aeonOnFinishJQueryLoading;document.getElementsByTagName('head')[0].appendChild(script)}else aeonCallback(aeonFunction(aeonNonCallbackArguments));";

    /* JavaScript to be wrapped around non-jQuery strings
     *
     * var aeonCallback = arguments[arguments.length-1];
     * var aeonNonCallbackArguments = (arguments.length == 1) ? [] : Array.prototype.slice.call(arguments, 0, arguments.length-2);
     * function aeonFunction() {
     *     // insert code here
     * }
     * aeonCallback(aeonFunction(aeonNonCallbackArguments));
     */
    private static final String DO_NOT_NEED_JQUERY_SCRIPT_BEFORE = "var aeonCallback=arguments[arguments.length-1];var aeonNonCallbackArguments=arguments.length==1?[]:Array.prototype.slice.call(arguments,0,arguments.length-2);function aeonFunction(){";
    private static final String DO_NOT_NEED_JQUERY_SCRIPT_AFTER = "}aeonCallback(aeonFunction(aeonNonCallbackArguments));";

    /**
     * Initializes a new instance of the {@link InjectJQueryScripter} class.
     *
     * @param successor The next finalizer for formatting.
     */
    InjectJQueryScripter(IJavaScriptFinalizer successor) {
        super(successor);
    }

    /**
     * Specifies the particular preparation formatting.
     *
     * @param javaScript The JavaScript code to format.
     * @return Formatted JavaScript code.
     * @throws IllegalArgumentException If {@code javaScript} is null.
     */
    @Override
    public String prepare(String javaScript) {
        if (javaScript == null) {
            throw new IllegalArgumentException("javaScript");
        }

        return javaScript.contains("$(") ?
                NEED_JQUERY_SCRIPT_BEFORE + successor.prepare(javaScript) + NEED_JQUERY_SCRIPT_AFTER :
                DO_NOT_NEED_JQUERY_SCRIPT_BEFORE + successor.prepare(javaScript) + DO_NOT_NEED_JQUERY_SCRIPT_AFTER;
    }
}
