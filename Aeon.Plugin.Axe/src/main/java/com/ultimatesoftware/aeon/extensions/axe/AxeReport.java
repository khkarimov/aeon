package com.ultimatesoftware.aeon.extensions.axe;

import java.util.Map;

/**
 * DTO for the Axe report.
 */
class AxeReport {
    private String team;
    private String product;
    private String page;
    private String branch;
    private String buildNumber;
    private String correlationId;
    private String screenshot;
    private Map<String, Object> report;

    public String getTeam() {
        return this.team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPage() {
        return this.page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getBranch() {
        return this.branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBuildNumber() {
        return this.buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getCorrelationId() {
        return this.correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getScreenshot() {
        return this.screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }

    public Map<String, Object> getReport() {
        return this.report;
    }

    public void setReport(Map<String, Object> report) {
        this.report = report;
    }
}
