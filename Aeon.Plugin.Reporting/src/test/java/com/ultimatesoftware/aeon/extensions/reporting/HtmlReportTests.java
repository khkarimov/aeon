package com.ultimatesoftware.aeon.extensions.reporting;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import com.ultimatesoftware.aeon.extensions.reporting.models.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class HtmlReportTests {

    private HtmlReport htmlReport;

    @Mock
    private IConfiguration configuration;

    @Mock
    private Logger log;

    private Report report;
    private String expectedStartTime;
    private String expectedEndTime;

    @BeforeEach
    void setup() {
        doReturn("log")
                .when(this.configuration)
                .getString(ReportingConfiguration.Keys.REPORTS_DIRECTORY, "");
        htmlReport = new HtmlReport(this.configuration);
        HtmlReport.log = this.log;

        this.report = new Report();
        long startTime = 1554482145460L;
        this.report.getTimer().setStartTime(startTime);
        this.report.getTimer().setEndTime(startTime + 122000);
        TestCase testCase1 = new TestCase();
        testCase1.setStatus("passed");
        TestCase testCase2 = new TestCase();
        testCase2.setStatus("failed");
        TestCase testCase3 = new TestCase();
        testCase3.setStatus("disabled");
        this.report.setSequence(Arrays.asList(testCase1, testCase2, testCase3));
        SimpleDateFormat uploadDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        this.expectedStartTime = uploadDateFormat.format(new Date(startTime));
        this.expectedEndTime = uploadDateFormat.format(new Date(startTime + 122000));
    }

    @Test
    void prepareReport_reportProvided_calculatesCountsAndTimings() {

        // Arrange

        // Act
        this.htmlReport.prepareReport(this.report);
        Report retrievedReport = this.htmlReport.getReport();

        // Assert
        assertEquals(1, retrievedReport.getCounts().getPassed());
        assertEquals(1, retrievedReport.getCounts().getFailed());
        assertEquals(1, retrievedReport.getCounts().getDisabled());
        assertEquals(122000, retrievedReport.getTimer().getDuration());
        assertEquals(this.expectedStartTime, retrievedReport.getTimer().getStarted());
        assertEquals(this.expectedEndTime, retrievedReport.getTimer().getStopped());
    }

    @Test
    void prepareReport_reportProvided_generatedJsonReport() {

        // Arrange

        // Act
        this.htmlReport.prepareReport(this.report);
        String retrievedJsonReport = this.htmlReport.getJsonReport();

        // Assert
        assertEquals(
                "{\"url\":null,\"correlationId\":null,\"name\":\"\",\"counts\":{\"passed\":1,\"failed\":1,\"disabled\":1,\"total\":3}" +
                        ",\"timer\":{\"duration\":122000,\"startTime\":1554482145460,\"endTime\":1554482267460,\"started\":\"" +
                        this.expectedStartTime + "\",\"stopped\":\"" + this.expectedEndTime + "\"},\"sequence\":[{\"threadId\":0,\"status\":\"passed\"" +
                        ",\"prefix\":\"\",\"description\":\"\",\"failedExpectations\":[],\"browserLogs\":null,\"startTime\":0,\"started\":\"\"," +
                        "\"stopped\":\"\",\"duration\":\"\",\"screenshotPath\":\"\",\"videoUrl\":\"\",\"steps\":[{\"name\":\"\",\"steps\":[]}]}," +
                        "{\"threadId\":0,\"status\":\"failed\",\"prefix\":\"\",\"description\":\"\",\"failedExpectations\":[],\"browserLogs\":null," +
                        "\"startTime\":0,\"started\":\"\",\"stopped\":\"\",\"duration\":\"\",\"screenshotPath\":\"\",\"videoUrl\":\"\"," +
                        "\"steps\":[{\"name\":\"\",\"steps\":[]}]},{\"threadId\":0,\"status\":\"disabled\",\"prefix\":\"\",\"description\":\"\"," +
                        "\"failedExpectations\":[],\"browserLogs\":null,\"startTime\":0,\"started\":\"\",\"stopped\":\"\",\"duration\":\"\"," +
                        "\"screenshotPath\":\"\",\"videoUrl\":\"\",\"steps\":[{\"name\":\"\",\"steps\":[]}]}]}",
                retrievedJsonReport);
    }
}
