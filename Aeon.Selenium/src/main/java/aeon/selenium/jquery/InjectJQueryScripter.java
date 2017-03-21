package aeon.selenium.jquery;

/**
 * Wraps JavaScript within a code to inject jquery.
 */
public class InjectJQueryScripter extends JavaScriptFinalizer {
    /* JavaScript to be wrapped around jQuery strings
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
    private static final String NeedJQueryScriptBefore = "var aeonCallback=arguments[arguments.length-1];var aeonNonCallbackArguments=arguments.length==1?[]:Array.prototype.slice.call(arguments,0,arguments.length-2);function aeonFunction(){";
    private static final String NeedJQueryScriptAfter = "}var aeonJQueryLoaded=false;function aeonOnFinishJQueryLoading(){if(aeonJQueryLoaded)return;aeonJQueryLoaded=true;var $=window.jquery;aeonCallback(aeonFunction(aeonNonCallbackArguments))}" +
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
    private static final String DoNotNeedJQueryScriptBefore = "var aeonCallback=arguments[arguments.length-1];var aeonNonCallbackArguments=arguments.length==1?[]:Array.prototype.slice.call(arguments,0,arguments.length-2);function aeonFunction(){";
    private static final String DoNotNeedJQueryScriptAfter = "}aeonCallback(aeonFunction(aeonNonCallbackArguments));";

    /**
     * Initializes a new instance of the {@link InjectJQueryScripter} class.
     *
     * @param successor The next finalizer for formatting.
     */
    public InjectJQueryScripter(IJavaScriptFinalizer successor) {
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

        // Commented out because this causes the unit tests to hang (a pop up opens and the test hangs until you hit the "ignore" button.
        // Debug.Assert(Successor != null, "Cannot use null reference for successor.");
        return javaScript.contains("$(") ?
                NeedJQueryScriptBefore + Successor.prepare(javaScript) + NeedJQueryScriptAfter :
                DoNotNeedJQueryScriptBefore + Successor.prepare(javaScript) + DoNotNeedJQueryScriptAfter;
    }
}
