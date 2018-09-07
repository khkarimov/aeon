package aeon.extensions.reporting;

import java.util.List;

public class HighLevelStep {

    public String name;
    public List<String> steps;

    public HighLevelStep(String name, List<String> steps) {
        this.name = name;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public List<String> getSteps() {
        return steps;
    }
}
