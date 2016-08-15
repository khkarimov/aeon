package echo.selenium.jQuery;

/**
 * Adds the :regex jQuery expression, if needed.
 */
public class JQueryRegexPluginRegistration extends JavaScriptFinalizer {
    /**
     * Initializes a new instance of the <see cref="JQueryRegexPluginRegistration"/> class.
     *
     * @param successor The next finalizer for formatting.
     */
    public JQueryRegexPluginRegistration(IJavaScriptFinalizer successor) {
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

        /* if(!jQuery.expr[':'].regex) {
             *     jQuery.expr[':'].regex = function(elem, index, match) {
             *         var matchParams = match[3].split(','),
             *             validLabels = /^(data|css):/,
             *             attr = {
             *                 method: matchParams[0].match(validLabels) ? matchParams[0].split(':')[0] : 'attr',
             *                 property: matchParams.shift().replace(validLabels,'')
             *             },
             *             regexFlags = 'ig',
             *             regex = new RegExp(matchParams.join('').replace(/^\s+|\s+$/g,''), regexFlags);
             *         return regex.test(jQuery(elem)[attr.method](attr.property));
             *     }
             * }
             */
        return javaScript.contains(":regex") ? ("if(!jQuery.expr[':'].regex)jQuery.expr[':'].regex=function(elem,index,match){var matchParams=match[3].split(','),validLabels=/^(data|css):/,attr={method:matchParams[0].match(validLabels)?matchParams[0].split(':')[0]:'attr',property:matchParams.shift().replace(validLabels,'')}," +
                "regexFlags='ig',regex=new RegExp(matchParams.join('').replace(/^\\s+|\\s+$/g,''),regexFlags);return regex.test(jQuery(elem)[attr.method](attr.property))};" + Successor.Prepare(javaScript)) : Successor.Prepare(javaScript);
    }
}
