package aeon.core.common.web.selectors;

/**
 * Instantiated from jQuery + int, jQuery - int operator overload.
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
     * Returns a {@link String} that represents the current {@link Object}.
     *
     * @return A {@link String} that represents the current {@link Object}.
     */
    @Override
    public String toString() {
        return getScript();
    }
}
