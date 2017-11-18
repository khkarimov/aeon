package aeon.selenium.jquery;

/**
 * Adds the :regex jquery expression, if needed.
 */
public class JQueryRegexPluginRegistration extends JavaScriptFinalizer {
    /**
     * Initializes a new instance of the {@link JQueryRegexPluginRegistration} class.
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
     * @throws IllegalArgumentException If {@code javaScript} is {@code null}.
     */
    @Override
    public String prepare(String javaScript) {
        if (javaScript == null) {
            throw new IllegalArgumentException("javaScript");
        }

        /* if(!jquery.expr[':'].regex) {
             *     jquery.expr[':'].regex = function(elem, index, match) {
             *         var matchParams = match[3].split(','),
             *             validLabels = /^(data|css):/,
             *             attr = {
             *                 method: matchParams[0].match(validLabels) ? matchParams[0].split(':')[0] : 'attr',
             *                 property: matchParams.shift().replace(validLabels,'')
             *             },
             *             regexFlags = 'ig',
             *             regex = new RegExp(matchParams.join('').replace(/^\s+|\s+$/g,''), regexFlags);
             *         return regex.test(jquery(elem)[attr.method](attr.property));
             *     }
             * }
             */
        return javaScript.contains(":regex") ? ("if(!jquery.expr[':'].regex)jquery.expr[':'].regex=function(elem,index,match){var matchParams=match[3].split(','),validLabels=/^(data|css):/,attr={method:matchParams[0].match(validLabels)?matchParams[0].split(':')[0]:'attr',property:matchParams.shift().replace(validLabels,'')}," +
                "regexFlags='ig',regex=new RegExp(matchParams.join('').replace(/^\\s+|\\s+$/g,''),regexFlags);return regex.test(jquery(elem)[attr.method](attr.property))};" + successor.prepare(javaScript)) : successor.prepare(javaScript);
    }
}
