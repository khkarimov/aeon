package com.ultimatesoftware.aeon.extensions.reporting.resultreportmodel;

/**
 * Details about failures.
 */
public class FailedExpectation {
    private String message;
    private String stack;

    /**
     * Returns the error message.
     *
     * @return The error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     *
     * @param message The error message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns the stack trace.
     *
     * @return The stack trace.
     */
    public String getStack() {
        return stack;
    }

    /**
     * Sets the stack trace.
     *
     * @param stack The stack trace.
     */
    public void setStack(String stack) {
        this.stack = stack;
    }
}
