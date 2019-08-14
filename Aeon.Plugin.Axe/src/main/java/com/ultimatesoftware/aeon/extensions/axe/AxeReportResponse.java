package com.ultimatesoftware.aeon.extensions.axe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Model for the response from the Axe report server.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
class AxeReportResponse {
    private String reportUrl;

    /**
     * Sets the report URL.
     *
     * @param reportUrl The report URL to set.
     */
    void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }

    /**
     * Returns the report URL.
     *
     * @return The report URL.
     */
    String getReportUrl() {
        return this.reportUrl;
    }
}
