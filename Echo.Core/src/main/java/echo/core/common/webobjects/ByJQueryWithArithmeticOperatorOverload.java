package echo.core.common.webobjects;

/**
 * Instantiated from JQuery + int, JQuery - int operator overload.
 */
public class ByJQueryWithArithmeticOperatorOverload {
    private String script;

    public ByJQueryWithArithmeticOperatorOverload(String script) {
        this.script = script;
    }

    /**
     * Gets the script.
     */
    protected final String getScript() {
        return script;
    }

    /**
     * Returns a <see cref="string"/> that represents the current <see cref="object"/>.
     *
     * @return A <see cref="string"/> that represents the current <see cref="object"/>.
     * <filterpriority>2</filterpriority>
     */
    @Override
    public String toString() {
        return getScript();
    }
}