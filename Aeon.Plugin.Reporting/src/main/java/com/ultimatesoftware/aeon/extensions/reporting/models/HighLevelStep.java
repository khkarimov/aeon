package com.ultimatesoftware.aeon.extensions.reporting.models;

import java.util.List;

/**
 * Models a high level test execution step which can contain multiple automation steps.
 */
public class HighLevelStep {

    private String name;
    private List<String> steps;

    /**
     * Creates a new high level step.
     *
     * @param name  The name of the high level step.
     * @param steps An array of automation steps.
     */
    public HighLevelStep(String name, List<String> steps) {
        this.name = name;
        this.steps = steps;
    }

    /**
     * Returns the name of the high level step.
     *
     * @return The name of the high level step.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the automation steps of this high level step.
     *
     * @return The automation steps of this high level step.
     */
    public List<String> getSteps() {
        return steps;
    }
}
