package aeon.extensions.reporting.resultreportmodel;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResultReportTests {

    @Test
    public void resultReport_onInstantiation_initializesFields() {
        // Arrange

        // Act
        ResultReport resultReport = new ResultReport();

        // Assert
        assertNotNull(resultReport.getCounts());
        assertNotNull(resultReport.getSequence());
        assertNotNull(resultReport.getTimer());
    }
}
