package echo.selenium.jQuery;

/**
 * Wraps JavaScript within a code to inject jQuery.
 */
public class InjectJQueryScripter extends JavaScriptFinalizer {
    /* JavaScript to be wrapped around JQuery strings
     *
     * var echoCallback = arguments[arguments.length-1];
     * var echoNonCallbackArguments = (arguments.length == 1) ? [] : Array.prototype.slice.call(arguments, 0, arguments.length-2);
     * function echoFunction() {
     *     // insert code here
     * }
     * var echoJQueryLoaded = false;
     * function echoOnFinishJQueryLoading() {
     *     if (echoJQueryLoaded) return;
     *     echoJQueryLoaded = true;
     *     var $ = window.jQuery;
     *     echoCallback(echoFunction(echoNonCallbackArguments));
     * }
     * if(!window.jQuery) {
     *     var script = document.createElement('script');
     *     script.type = 'text/javascript';
     *     script.src = 'https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js';
     *     script.onreadystatechange= function () { if (this.readyState == 'loaded' || this.readyState == 'complete') echoOnFinishJQueryLoading(); };
     *     script.onload = echoOnFinishJQueryLoading;
     *     document.getElementsByTagName('head')[0].appendChild(script);
     * } else
     *     echoCallback(echoFunction(echoNonCallbackArguments));
     */
    private static final String NeedJQueryScriptBefore = "var echoCallback=arguments[arguments.length-1];var echoNonCallbackArguments=arguments.length==1?[]:Array.prototype.slice.call(arguments,0,arguments.length-2);function echoFunction(){";
    private static final String NeedJQueryScriptAfter = "}var echoJQueryLoaded=false;function echoOnFinishJQueryLoading(){if(echoJQueryLoaded)return;echoJQueryLoaded=true;var $=window.jQuery;echoCallback(echoFunction(echoNonCallbackArguments))}" +
            "if(!window.jQuery){var script=document.createElement('script');script.type='text/javascript';script.src='https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js';script.onreadystatechange=function(){if(this.readyState=='loaded'||this.readyState=='complete')" +
            "echoOnFinishJQueryLoading()};script.onload=echoOnFinishJQueryLoading;document.getElementsByTagName('head')[0].appendChild(script)}else echoCallback(echoFunction(echoNonCallbackArguments));";

    /* JavaScript to be wrapped around non-JQuery strings
     *
     * var echoCallback = arguments[arguments.length-1];
     * var echoNonCallbackArguments = (arguments.length == 1) ? [] : Array.prototype.slice.call(arguments, 0, arguments.length-2);
     * function echoFunction() {
     *     // insert code here
     * }
     * echoCallback(echoFunction(echoNonCallbackArguments));
     */
    private static final String DoNotNeedJQueryScriptBefore = "var echoCallback=arguments[arguments.length-1];var echoNonCallbackArguments=arguments.length==1?[]:Array.prototype.slice.call(arguments,0,arguments.length-2);function echoFunction(){";
    private static final String DoNotNeedJQueryScriptAfter = "}echoCallback(echoFunction(echoNonCallbackArguments));";

    /**
     * Initializes a new instance of the <see cref="InjectJQueryScripter"/> class.
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
     * @throws IllegalArgumentException If <paramref name="javaScript"/> is <see langword="null"/>.
     */
    @Override
    public String Prepare(String javaScript) {
        if (javaScript == null) {
            throw new IllegalArgumentException("javaScript");
        }

        // Commented out because this causes the unit tests to hang (a pop up opens and the test hangs until you hit the "ignore" button.
        // Debug.Assert(Successor != null, "Cannot use null reference for successor.");
        return javaScript.contains("$(") ?
                NeedJQueryScriptBefore + Successor.Prepare(javaScript) + NeedJQueryScriptAfter :
                DoNotNeedJQueryScriptBefore + Successor.Prepare(javaScript) + DoNotNeedJQueryScriptAfter;
    }
}
