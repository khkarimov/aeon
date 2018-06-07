package aeon.extensions.reporting;

public class Scenario {
    private String scenarioName;
    private String startTime;
    private String status;
    private String errorMessage;
    private String moduleName;

    public String getScenarioName() {
        return this.scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        String errorMessageSanitized = this.errorMessage;
        errorMessageSanitized = errorMessageSanitized.replace("&", "&amp;");
        errorMessageSanitized = errorMessageSanitized.replace("<","&lt;");
        errorMessageSanitized = errorMessageSanitized.replace(">", "&gt;");
        return errorMessageSanitized;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

}

