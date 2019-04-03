package com.ultimatesoftware.aeon.extensions.slack;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.ReportingConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.models.Report;
import com.ultimatesoftware.aeon.extensions.reporting.models.TestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class ImageReportTests {

    private ImageReport imageReport;
    private Report report;
    private TestCase testCase1;
    private TestCase testCase2;

    @Mock
    private IConfiguration configuration;

    @BeforeEach
    void setUp() {
        doReturn("Chrome")
                .when(this.configuration)
                .getString("aeon.browser", "");
        doReturn("google.com")
                .when(this.configuration)
                .getString("aeon.environment", "");
        doReturn(true)
                .when(this.configuration)
                .getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true);
        doReturn(200.0)
                .when(this.configuration)
                .getDouble(SlackConfiguration.Keys.ERROR_MESSAGE_CHARACTER_LIMIT, 300);

        imageReport = new ImageReport(this.configuration);
        testCase1 = new TestCase();
        testCase1.setPrefix("Prefix 1");
        testCase1.setDescription("test name 1");
        testCase1.setStatus("passed");
        testCase2 = new TestCase();
        testCase2.setPrefix("Prefix 2");
        testCase2.setDescription("test name 2");
        testCase2.setStatus("passed");
        report = new Report();
        report.setName("Report Test Name");
        report.getCounts().setPassed(2);
        report.getTimer().setDuration(122000);
        report.setSequence(Arrays.asList(testCase1, testCase2));
    }

    @Test
    void buildReportHtml_passedTestsOnly_ReturnsValidHtml() {

        // Arrange

        // Act
        String html = imageReport.buildReportHtml(report);

        // Assert
        assertEquals("<head><style> @page { size: A4 landscape;} table {width:100%;font-size:x-small}, th, td {font-family: Trebuchet MS,Arial, Helvetica, sans-serif;border: 1px solid black; border-collapse: collapse;} th," +
                " td { padding: 5px; text-align: left;}table#t01 tr.alt td {background-color: #E0ECF8;} table#t01 th{background-color: #0B2161;color: white;}table#t03 tr.alt td {background-color: #E0ECF8;} table#t03 th{background-color:" +
                " #ff2222;color: white;}table#t04 tr.alt td {background-color: #E0ECF8;} table#t04 th{background-color: #4AA02C;color: white;}table#t02 td {font-size:x-small;padding:0px;margin:0px;margin-auto:0px;mso-line-height-rule:" +
                " exactly;line-height:100%;}table#t02 th{background-color: #DDFADA;color: black;text-align:center;},p{font-family: Trebuchet MS, Arial, Helvetica, sans-serif;border}</style><style>h4{font-family: Trebuchet MS,Arial," +
                " Helvetica, sans-serif;text-align: left;}</style></head><table id=t02><th>Test Configuration</th><td><p>Browser: Chrome</p><p>Environment URL: google.com</p></table><h4>Overall Summary</h4><table id=t01><th>Suite Name" +
                "</th><th>Total Tests</th><th>Passed</th><th>Failed</th><th>Broken</th><th>Skipped</th><th>Total Time</th><tr><td>Report Test Name</td><td>2</td><td>2</td><td>0</td><td>0</td><td>0</td><td>2 minutes 2 seconds</td></tr>" +
                "</table><h4>Pass List</h4><table id=t04><th>Class Name</th><th>Test Name</th><th>Status</th><tr><td>Prefix 1</td><td>test name 1</td><td><font color=\"green\">PASSED</font></td></tr><tr><td>Prefix 2</td><td>test name 2" +
                "</td><td><font color=\"green\">PASSED</font></td></tr></table><br>", html);
    }

    @Test
    void buildReportHtml_failedTestsOnly_ReturnsValidHtml() {

        // Arrange
        testCase1.setError("error message 1", "stack trace");
        testCase1.setStatus("failed");
        testCase2.setError("error message 2", "stack trace 2");
        testCase2.setStatus("failed");
        report.getCounts().setPassed(0);
        report.getCounts().setFailed(2);

        // Act
        String html = imageReport.buildReportHtml(report);

        // Assert
        assertEquals("<head><style> @page { size: A4 landscape;} table {width:100%;font-size:x-small}, th, td {font-family: Trebuchet MS,Arial, Helvetica, sans-serif;border: 1px solid black; border-collapse: collapse;} th," +
                " td { padding: 5px; text-align: left;}table#t01 tr.alt td {background-color: #E0ECF8;} table#t01 th{background-color: #0B2161;color: white;}table#t03 tr.alt td {background-color: #E0ECF8;} table#t03 th{background-color:" +
                " #ff2222;color: white;}table#t04 tr.alt td {background-color: #E0ECF8;} table#t04 th{background-color: #4AA02C;color: white;}table#t02 td {font-size:x-small;padding:0px;margin:0px;margin-auto:0px;mso-line-height-rule:" +
                " exactly;line-height:100%;}table#t02 th{background-color: #DDFADA;color: black;text-align:center;},p{font-family: Trebuchet MS, Arial, Helvetica, sans-serif;border}</style><style>h4{font-family: Trebuchet MS,Arial," +
                " Helvetica, sans-serif;text-align: left;}</style></head><table id=t02><th>Test Configuration</th><td><p>Browser: Chrome</p><p>Environment URL: google.com</p></table><h4>Overall Summary</h4><table id=t01><th>Suite Name" +
                "</th><th>Total Tests</th><th>Passed</th><th>Failed</th><th>Broken</th><th>Skipped</th><th>Total Time</th><tr><td>Report Test Name</td><td>2</td><td>0</td><td>2</td><td>0</td><td>0</td><td>2 minutes 2 seconds</td></tr>" +
                "</table><h4>Failed List</h4><table id=t03><th>Class Name</th><th>Test Name</th><th>Status</th><th>Error Message</th><tr><td>Prefix 1</td><td>test name 1</td><td><font color=\"red\">FAILED</font></td><td style='width:150px;" +
                " max-width:150px'>error message 1</td></tr><tr><td>Prefix 2</td><td>test name 2</td><td><font color=\"red\">FAILED</font></td><td style='width:150px; max-width:150px'>error message 2</td></tr></table>", html);
    }

    @Test
    void buildReportHtml_passedAndFailedTests_ReturnsValidHtml() {

        // Arrange
        testCase2.setError("error message 2", "stack trace 2");
        testCase2.setStatus("failed");
        report.getCounts().setPassed(1);
        report.getCounts().setFailed(1);

        // Act
        String html = imageReport.buildReportHtml(report);

        // Assert
        assertEquals("<head><style> @page { size: A4 landscape;} table {width:100%;font-size:x-small}, th, td {font-family: Trebuchet MS,Arial, Helvetica, sans-serif;border: 1px solid black; border-collapse: collapse;} th," +
                " td { padding: 5px; text-align: left;}table#t01 tr.alt td {background-color: #E0ECF8;} table#t01 th{background-color: #0B2161;color: white;}table#t03 tr.alt td {background-color: #E0ECF8;} table#t03 th{background-color:" +
                " #ff2222;color: white;}table#t04 tr.alt td {background-color: #E0ECF8;} table#t04 th{background-color: #4AA02C;color: white;}table#t02 td {font-size:x-small;padding:0px;margin:0px;margin-auto:0px;mso-line-height-rule:" +
                " exactly;line-height:100%;}table#t02 th{background-color: #DDFADA;color: black;text-align:center;},p{font-family: Trebuchet MS, Arial, Helvetica, sans-serif;border}</style><style>h4{font-family: Trebuchet MS,Arial," +
                " Helvetica, sans-serif;text-align: left;}</style></head><table id=t02><th>Test Configuration</th><td><p>Browser: Chrome</p><p>Environment URL: google.com</p></table><h4>Overall Summary</h4><table id=t01><th>Suite Name" +
                "</th><th>Total Tests</th><th>Passed</th><th>Failed</th><th>Broken</th><th>Skipped</th><th>Total Time</th><tr><td>Report Test Name</td><td>2</td><td>1</td><td>1</td><td>0</td><td>0</td><td>2 minutes 2 seconds</td></tr>" +
                "</table><h4>Failed List</h4><table id=t03><th>Class Name</th><th>Test Name</th><th>Status</th><th>Error Message</th><tr><td>Prefix 2</td><td>test name 2</td><td><font color=\"red\">FAILED</font></td><td style='width:150px;" +
                " max-width:150px'>error message 2</td></tr></table><h4>Pass List</h4><table id=t04><th>Class Name</th><th>Test Name</th><th>Status</th><tr><td>Prefix 1</td><td>test name 1</td><td><font color=\"green\">PASSED</font></td></tr></table><br>", html);
    }

    @Test
    void buildReportHtml_passedAndFailedTestsAndNoReportName_ReturnsValidHtml() {

        // Arrange
        testCase2.setError("error message 2", "stack trace 2");
        testCase2.setStatus("failed");
        report.setName(null);
        report.getCounts().setPassed(1);
        report.getCounts().setFailed(1);

        // Act
        String html = imageReport.buildReportHtml(report);

        // Assert
        assertEquals("<head><style> @page { size: A4 landscape;} table {width:100%;font-size:x-small}, th, td {font-family: Trebuchet MS,Arial, Helvetica, sans-serif;border: 1px solid black; border-collapse: collapse;} th," +
                " td { padding: 5px; text-align: left;}table#t01 tr.alt td {background-color: #E0ECF8;} table#t01 th{background-color: #0B2161;color: white;}table#t03 tr.alt td {background-color: #E0ECF8;} table#t03 th{background-color:" +
                " #ff2222;color: white;}table#t04 tr.alt td {background-color: #E0ECF8;} table#t04 th{background-color: #4AA02C;color: white;}table#t02 td {font-size:x-small;padding:0px;margin:0px;margin-auto:0px;mso-line-height-rule:" +
                " exactly;line-height:100%;}table#t02 th{background-color: #DDFADA;color: black;text-align:center;},p{font-family: Trebuchet MS, Arial, Helvetica, sans-serif;border}</style><style>h4{font-family: Trebuchet MS,Arial," +
                " Helvetica, sans-serif;text-align: left;}</style></head><table id=t02><th>Test Configuration</th><td><p>Browser: Chrome</p><p>Environment URL: google.com</p></table><h4>Overall Summary</h4><table id=t01><th>Total Tests" +
                "</th><th>Passed</th><th>Failed</th><th>Skipped</th><th>Total Time</th><tr><td>2</td><td>1</td><td>1</td><td>0</td><td>2 minutes 2 seconds</td></tr></table><h4>Failed List</h4><table id=t03><th>Class Name</th><th>Test Name" +
                "</th><th>Status</th><th>Error Message</th><tr><td>Prefix 2</td><td>test name 2</td><td><font color=\"red\">FAILED</font></td><td style='width:150px; max-width:150px'>error message 2</td></tr></table><h4>Pass List</h4><table" +
                " id=t04><th>Class Name</th><th>Test Name</th><th>Status</th><tr><td>Prefix 1</td><td>test name 1</td><td><font color=\"green\">PASSED</font></td></tr></table><br>", html);
    }

    @Test
    void buildReportHtml_passedAndFailedTestsAndEmptyReportName_ReturnsValidHtml() {

        // Arrange
        testCase2.setError("error message 2", "stack trace 2");
        testCase2.setStatus("failed");
        report.setName("");
        report.getCounts().setPassed(1);
        report.getCounts().setFailed(1);

        // Act
        String html = imageReport.buildReportHtml(report);

        // Assert
        assertEquals("<head><style> @page { size: A4 landscape;} table {width:100%;font-size:x-small}, th, td {font-family: Trebuchet MS,Arial, Helvetica, sans-serif;border: 1px solid black; border-collapse: collapse;} th," +
                " td { padding: 5px; text-align: left;}table#t01 tr.alt td {background-color: #E0ECF8;} table#t01 th{background-color: #0B2161;color: white;}table#t03 tr.alt td {background-color: #E0ECF8;} table#t03 th{background-color:" +
                " #ff2222;color: white;}table#t04 tr.alt td {background-color: #E0ECF8;} table#t04 th{background-color: #4AA02C;color: white;}table#t02 td {font-size:x-small;padding:0px;margin:0px;margin-auto:0px;mso-line-height-rule:" +
                " exactly;line-height:100%;}table#t02 th{background-color: #DDFADA;color: black;text-align:center;},p{font-family: Trebuchet MS, Arial, Helvetica, sans-serif;border}</style><style>h4{font-family: Trebuchet MS,Arial," +
                " Helvetica, sans-serif;text-align: left;}</style></head><table id=t02><th>Test Configuration</th><td><p>Browser: Chrome</p><p>Environment URL: google.com</p></table><h4>Overall Summary</h4><table id=t01><th>Total Tests" +
                "</th><th>Passed</th><th>Failed</th><th>Skipped</th><th>Total Time</th><tr><td>2</td><td>1</td><td>1</td><td>0</td><td>2 minutes 2 seconds</td></tr></table><h4>Failed List</h4><table id=t03><th>Class Name</th><th>Test Name" +
                "</th><th>Status</th><th>Error Message</th><tr><td>Prefix 2</td><td>test name 2</td><td><font color=\"red\">FAILED</font></td><td style='width:150px; max-width:150px'>error message 2</td></tr></table><h4>Pass List</h4><table" +
                " id=t04><th>Class Name</th><th>Test Name</th><th>Status</th><tr><td>Prefix 1</td><td>test name 1</td><td><font color=\"green\">PASSED</font></td></tr></table><br>", html);
    }

    @Test
    void buildReportHtml_passedAndFailedTestsAndNoDisplayClasses_ReturnsValidHtml() {

        // Arrange
        doReturn(false)
                .when(this.configuration)
                .getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true);
        this.imageReport = new ImageReport(this.configuration);
        testCase2.setError("error message 2", "stack trace 2");
        testCase2.setStatus("failed");
        report.getCounts().setPassed(1);
        report.getCounts().setFailed(1);

        // Act
        String html = imageReport.buildReportHtml(report);

        // Assert
        assertEquals("<head><style> @page { size: A4 landscape;} table {width:100%;font-size:x-small}, th, td {font-family: Trebuchet MS,Arial, Helvetica, sans-serif;border: 1px solid black; border-collapse: collapse;} th," +
                " td { padding: 5px; text-align: left;}table#t01 tr.alt td {background-color: #E0ECF8;} table#t01 th{background-color: #0B2161;color: white;}table#t03 tr.alt td {background-color: #E0ECF8;} table#t03 th{background-color:" +
                " #ff2222;color: white;}table#t04 tr.alt td {background-color: #E0ECF8;} table#t04 th{background-color: #4AA02C;color: white;}table#t02 td {font-size:x-small;padding:0px;margin:0px;margin-auto:0px;mso-line-height-rule:" +
                " exactly;line-height:100%;}table#t02 th{background-color: #DDFADA;color: black;text-align:center;},p{font-family: Trebuchet MS, Arial, Helvetica, sans-serif;border}</style><style>h4{font-family: Trebuchet MS,Arial," +
                " Helvetica, sans-serif;text-align: left;}</style></head><table id=t02><th>Test Configuration</th><td><p>Browser: Chrome</p><p>Environment URL: google.com</p></table><h4>Overall Summary</h4><table id=t01><th>Suite Name" +
                "</th><th>Total Tests</th><th>Passed</th><th>Failed</th><th>Broken</th><th>Skipped</th><th>Total Time</th><tr><td>Report Test Name</td><td>2</td><td>1</td><td>1</td><td>0</td><td>0</td><td>2 minutes 2 seconds</td></tr>" +
                "</table><h4>Failed List</h4><table id=t03><th>Test Name</th><th>Status</th><th>Error Message</th><tr><td>test name 2</td><td><font color=\"red\">FAILED</font></td><td style='width:150px; max-width:150px'>error message 2" +
                "</td></tr></table><h4>Pass List</h4><table id=t04><th>Test Name</th><th>Status</th><tr><td>test name 1</td><td><font color=\"green\">PASSED</font></td></tr></table><br>", html);
    }

    @Test
    void buildReportHtml_passedTestsOnlyAndNoDisplayClasses_ReturnsValidHtml() {

        // Arrange
        doReturn(false)
                .when(this.configuration)
                .getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true);
        this.imageReport = new ImageReport(this.configuration);

        // Act
        String html = imageReport.buildReportHtml(report);

        // Assert
        assertEquals("<head><style> @page { size: A4 landscape;} table {width:100%;font-size:x-small}, th, td {font-family: Trebuchet MS,Arial, Helvetica, sans-serif;border: 1px solid black; border-collapse: collapse;} th," +
                " td { padding: 5px; text-align: left;}table#t01 tr.alt td {background-color: #E0ECF8;} table#t01 th{background-color: #0B2161;color: white;}table#t03 tr.alt td {background-color: #E0ECF8;} table#t03 th{background-color:" +
                " #ff2222;color: white;}table#t04 tr.alt td {background-color: #E0ECF8;} table#t04 th{background-color: #4AA02C;color: white;}table#t02 td {font-size:x-small;padding:0px;margin:0px;margin-auto:0px;mso-line-height-rule:" +
                " exactly;line-height:100%;}table#t02 th{background-color: #DDFADA;color: black;text-align:center;},p{font-family: Trebuchet MS, Arial, Helvetica, sans-serif;border}</style><style>h4{font-family: Trebuchet MS,Arial," +
                " Helvetica, sans-serif;text-align: left;}</style></head><table id=t02><th>Test Configuration</th><td><p>Browser: Chrome</p><p>Environment URL: google.com</p></table><h4>Overall Summary</h4><table id=t01><th>Suite Name" +
                "</th><th>Total Tests</th><th>Passed</th><th>Failed</th><th>Broken</th><th>Skipped</th><th>Total Time</th><tr><td>Report Test Name</td><td>2</td><td>2</td><td>0</td><td>0</td><td>0</td><td>2 minutes 2 seconds</td></tr>" +
                "</table><h4>Pass List</h4><table id=t04><th>Test Name</th><th>Status</th><tr><td>test name 1</td><td><font color=\"green\">PASSED</font></td></tr><tr><td>test name 2</td><td><font color=\"green\">PASSED</font></td></tr></table><br>", html);
    }

    @Test
    void buildReportHtml_failedTestsOnlyAndNoDisplayClasses_ReturnsValidHtml() {

        // Arrange
        doReturn(false)
                .when(this.configuration)
                .getBoolean(ReportingConfiguration.Keys.DISPLAY_CLASSNAME, true);
        this.imageReport = new ImageReport(this.configuration);
        testCase1.setError("error message 1", "stack trace");
        testCase1.setStatus("failed");
        testCase2.setError("error message 2", "stack trace 2");
        testCase2.setStatus("failed");
        report.getCounts().setPassed(0);
        report.getCounts().setFailed(2);

        // Act
        String html = imageReport.buildReportHtml(report);

        // Assert
        assertEquals("<head><style> @page { size: A4 landscape;} table {width:100%;font-size:x-small}, th, td {font-family: Trebuchet MS,Arial, Helvetica, sans-serif;border: 1px solid black; border-collapse: collapse;} th," +
                " td { padding: 5px; text-align: left;}table#t01 tr.alt td {background-color: #E0ECF8;} table#t01 th{background-color: #0B2161;color: white;}table#t03 tr.alt td {background-color: #E0ECF8;} table#t03 th{background-color:" +
                " #ff2222;color: white;}table#t04 tr.alt td {background-color: #E0ECF8;} table#t04 th{background-color: #4AA02C;color: white;}table#t02 td {font-size:x-small;padding:0px;margin:0px;margin-auto:0px;mso-line-height-rule:" +
                " exactly;line-height:100%;}table#t02 th{background-color: #DDFADA;color: black;text-align:center;},p{font-family: Trebuchet MS, Arial, Helvetica, sans-serif;border}</style><style>h4{font-family: Trebuchet MS,Arial," +
                " Helvetica, sans-serif;text-align: left;}</style></head><table id=t02><th>Test Configuration</th><td><p>Browser: Chrome</p><p>Environment URL: google.com</p></table><h4>Overall Summary</h4><table id=t01><th>Suite Name" +
                "</th><th>Total Tests</th><th>Passed</th><th>Failed</th><th>Broken</th><th>Skipped</th><th>Total Time</th><tr><td>Report Test Name</td><td>2</td><td>0</td><td>2</td><td>0</td><td>0</td><td>2 minutes 2 seconds</td></tr>" +
                "</table><h4>Failed List</h4><table id=t03><th>Test Name</th><th>Status</th><th>Error Message</th><tr><td>test name 1</td><td><font color=\"red\">FAILED</font></td><td style='width:150px; max-width:150px'>error message 1" +
                "</td></tr><tr><td>test name 2</td><td><font color=\"red\">FAILED</font></td><td style='width:150px; max-width:150px'>error message 2</td></tr></table>", html);
    }

    @Test
    void buildReportHtml_passedFailedAndDisabledTests_ReturnsValidHtml() {

        // Arrange
        testCase2.setError("error message", "stack trace");
        testCase2.setStatus("failed");
        TestCase testCase3 = new TestCase();
        testCase3.setPrefix("Prefix 3");
        testCase3.setDescription("test name 3");
        testCase3.setStatus("disabled");
        report.setSequence(Arrays.asList(this.testCase1, this.testCase2, testCase3));
        report.getCounts().setPassed(1);
        report.getCounts().setFailed(1);
        report.getCounts().setDisabled(1);

        // Act
        String html = imageReport.buildReportHtml(report);

        // Assert
        assertEquals("<head><style> @page { size: A4 landscape;} table {width:100%;font-size:x-small}, th, td {font-family: Trebuchet MS,Arial, Helvetica, sans-serif;border: 1px solid black; border-collapse: collapse;} th," +
                " td { padding: 5px; text-align: left;}table#t01 tr.alt td {background-color: #E0ECF8;} table#t01 th{background-color: #0B2161;color: white;}table#t03 tr.alt td {background-color: #E0ECF8;} table#t03 th{background-color:" +
                " #ff2222;color: white;}table#t04 tr.alt td {background-color: #E0ECF8;} table#t04 th{background-color: #4AA02C;color: white;}table#t02 td {font-size:x-small;padding:0px;margin:0px;margin-auto:0px;mso-line-height-rule:" +
                " exactly;line-height:100%;}table#t02 th{background-color: #DDFADA;color: black;text-align:center;},p{font-family: Trebuchet MS, Arial, Helvetica, sans-serif;border}</style><style>h4{font-family: Trebuchet MS,Arial," +
                " Helvetica, sans-serif;text-align: left;}</style></head><table id=t02><th>Test Configuration</th><td><p>Browser: Chrome</p><p>Environment URL: google.com</p></table><h4>Overall Summary</h4><table id=t01><th>Suite Name" +
                "</th><th>Total Tests</th><th>Passed</th><th>Failed</th><th>Broken</th><th>Skipped</th><th>Total Time</th><tr><td>Report Test Name</td><td>3</td><td>1</td><td>1</td><td>0</td><td>1</td><td>2 minutes 2 seconds</td></tr>" +
                "</table><h4>Failed List</h4><table id=t03><th>Class Name</th><th>Test Name</th><th>Status</th><th>Error Message</th><tr><td>Prefix 2</td><td>test name 2</td><td><font color=\"red\">FAILED</font></td><td style='width:150px;" +
                " max-width:150px'>error message</td></tr><tr><td>Prefix 3</td><td>test name 3</td><td><font color=\"black\">DISABLED</font></td><td style='width:150px; max-width:150px'></td></tr></table><h4>Pass List</h4><table id=t04>" +
                "<th>Class Name</th><th>Test Name</th><th>Status</th><tr><td>Prefix 1</td><td>test name 1</td><td><font color=\"green\">PASSED</font></td></tr></table><br>", html);
    }

    @Test
    void buildReportHtml_passedAndDisabledTests_ReturnsValidHtml() {

        // Arrange
        testCase2.setStatus("disabled");
        report.getCounts().setPassed(1);
        report.getCounts().setDisabled(1);

        // Act
        String html = imageReport.buildReportHtml(report);

        // Assert
        assertEquals("<head><style> @page { size: A4 landscape;} table {width:100%;font-size:x-small}, th, td {font-family: Trebuchet MS,Arial, Helvetica, sans-serif;border: 1px solid black; border-collapse: collapse;} th," +
                " td { padding: 5px; text-align: left;}table#t01 tr.alt td {background-color: #E0ECF8;} table#t01 th{background-color: #0B2161;color: white;}table#t03 tr.alt td {background-color: #E0ECF8;} table#t03 th{background-color:" +
                " #ff2222;color: white;}table#t04 tr.alt td {background-color: #E0ECF8;} table#t04 th{background-color: #4AA02C;color: white;}table#t02 td {font-size:x-small;padding:0px;margin:0px;margin-auto:0px;mso-line-height-rule:" +
                " exactly;line-height:100%;}table#t02 th{background-color: #DDFADA;color: black;text-align:center;},p{font-family: Trebuchet MS, Arial, Helvetica, sans-serif;border}</style><style>h4{font-family: Trebuchet MS,Arial," +
                " Helvetica, sans-serif;text-align: left;}</style></head><table id=t02><th>Test Configuration</th><td><p>Browser: Chrome</p><p>Environment URL: google.com</p></table><h4>Overall Summary</h4><table id=t01><th>Suite Name" +
                "</th><th>Total Tests</th><th>Passed</th><th>Failed</th><th>Broken</th><th>Skipped</th><th>Total Time</th><tr><td>Report Test Name</td><td>2</td><td>1</td><td>0</td><td>0</td><td>1</td><td>2 minutes 2 seconds</td></tr>" +
                "</table><h4>Failed List</h4><table id=t03><th>Class Name</th><th>Test Name</th><th>Status</th><th>Error Message</th><tr><td>Prefix 2</td><td>test name 2</td><td><font color=\"black\">DISABLED</font></td><td style='width:150px;" +
                " max-width:150px'></td></tr></table><h4>Pass List</h4><table id=t04><th>Class Name</th><th>Test Name</th><th>Status</th><tr><td>Prefix 1</td><td>test name 1</td><td><font color=\"green\">PASSED</font></td></tr></table><br>", html);
    }

    @Test
    void buildReportHtml_passedFailedAndBrokenTests_ReturnsValidHtml() {

        // Arrange
        testCase2.setError("error message", "stack trace");
        testCase2.setStatus("failed");
        TestCase testCase3 = new TestCase();
        testCase3.setPrefix("Prefix 3");
        testCase3.setDescription("test name 3");
        testCase3.setStatus("broken");
        report.setSequence(Arrays.asList(this.testCase1, this.testCase2, testCase3));
        report.getCounts().setPassed(1);
        report.getCounts().setFailed(2);
        report.getCounts().setDisabled(0);

        // Act
        String html = imageReport.buildReportHtml(report);

        // Assert
        assertEquals("<head><style> @page { size: A4 landscape;} table {width:100%;font-size:x-small}, th, td {font-family: Trebuchet MS,Arial, Helvetica, sans-serif;border: 1px solid black; border-collapse: collapse;} th," +
                " td { padding: 5px; text-align: left;}table#t01 tr.alt td {background-color: #E0ECF8;} table#t01 th{background-color: #0B2161;color: white;}table#t03 tr.alt td {background-color: #E0ECF8;} table#t03 th{background-color:" +
                " #ff2222;color: white;}table#t04 tr.alt td {background-color: #E0ECF8;} table#t04 th{background-color: #4AA02C;color: white;}table#t02 td {font-size:x-small;padding:0px;margin:0px;margin-auto:0px;mso-line-height-rule:" +
                " exactly;line-height:100%;}table#t02 th{background-color: #DDFADA;color: black;text-align:center;},p{font-family: Trebuchet MS, Arial, Helvetica, sans-serif;border}</style><style>h4{font-family: Trebuchet MS,Arial," +
                " Helvetica, sans-serif;text-align: left;}</style></head><table id=t02><th>Test Configuration</th><td><p>Browser: Chrome</p><p>Environment URL: google.com</p></table><h4>Overall Summary</h4><table id=t01><th>Suite Name" +
                "</th><th>Total Tests</th><th>Passed</th><th>Failed</th><th>Broken</th><th>Skipped</th><th>Total Time</th><tr><td>Report Test Name</td><td>3</td><td>1</td><td>2</td><td>0</td><td>0</td><td>2 minutes 2 seconds</td></tr>" +
                "</table><h4>Failed List</h4><table id=t03><th>Class Name</th><th>Test Name</th><th>Status</th><th>Error Message</th><tr><td>Prefix 2</td><td>test name 2</td><td><font color=\"red\">FAILED</font></td><td style='width:150px;" +
                " max-width:150px'>error message</td></tr><tr><td>Prefix 3</td><td>test name 3</td><td><font color=\"#B7950B\">BROKEN</font></td><td style='width:150px; max-width:150px'></td></tr></table><h4>Pass List</h4><table id=t04>" +
                "<th>Class Name</th><th>Test Name</th><th>Status</th><tr><td>Prefix 1</td><td>test name 1</td><td><font color=\"green\">PASSED</font></td></tr></table><br>", html);
    }
}
