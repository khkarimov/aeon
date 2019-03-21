package com.ultimatesoftware.aeon.core.common.web.selectors;

/**
 * Instantiated from JQuery + int, JQuery - int operator overload.
 */
public class ByJQueryWithArithmeticOperatorOverload {

    private String script;

    /**
     *Initializes a new instance of the {@link ByJQueryWithArithmeticOperatorOverload} class.
     * @param script the string input.
     */
    public ByJQueryWithArithmeticOperatorOverload(String script) {
        this.script = script;
    }

    /**
     * Gets the script.
     * @return the script.
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
