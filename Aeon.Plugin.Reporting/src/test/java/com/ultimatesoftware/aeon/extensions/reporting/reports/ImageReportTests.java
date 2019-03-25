package com.ultimatesoftware.aeon.extensions.reporting.reports;

import com.ultimatesoftware.aeon.core.common.interfaces.IConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.ReportingConfiguration;
import com.ultimatesoftware.aeon.extensions.reporting.models.ReportDetails;
import com.ultimatesoftware.aeon.extensions.reporting.models.ScenarioDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;


public class ImageReportTests {
    //TODO: add assertions to tests or refactor tests


    private ImageReport imageReport;

    @BeforeEach
    public void setUp() {
        imageReport = new ImageReport();
    }

    @Test
    public void setConfiguration_HappyPath() {

        // Arrange
        IConfiguration configurationMock = mock(IConfiguration.class);
        when(configurationMock.getBoolean(any(ReportingConfiguration.Keys.DISPLAY_CLASSNAME.getClass()),anyBoolean())).thenReturn(true);
        boolean fieldValue = false;

        // Act
        imageReport.setConfiguration(configurationMock, configurationMock);
        try {
            Field displayClassName = ImageReport.class.getDeclaredField("displayClassName");
            displayClassName.setAccessible(true);
            fieldValue = (boolean) displayClassName.get(imageReport);
        } catch (Exception e ){
            System.out.println(e);
            fail();
        }

        // Assert
        assertTrue(fieldValue);


    }

    @Test
    public void buildImageReport_PassedTests_HappyPath() {

        // Arrange
        Iterator scenarioIteratorMock = mock(Iterator.class);
        ScenarioDetails scenarioMock = mock(ScenarioDetails.class);
        Queue<ScenarioDetails> scenariosMock = mock(Queue.class);
        ReportDetails reportDetailsMock = mock(ReportDetails.class);
        imageReport.setReportDetails(reportDetailsMock);
        when(reportDetailsMock.getNumberOfPassedTests()).thenReturn(1);
        when(reportDetailsMock.getScenarios()).thenReturn(scenariosMock);
        when(scenariosMock.iterator()).thenReturn(scenarioIteratorMock);
        when(scenarioIteratorMock.hasNext()).thenReturn(true, false);
        when(scenarioIteratorMock.next()).thenReturn(scenarioMock);
        when(scenarioMock.getStatus()).thenReturn("PASSED");

        // Act
        imageReport.buildImageReport("title");

        // Assert

    }

    @Test
    public void buildImageReport_getSuiteNameNotNullFailedTests_HappyPath() {

        // Arrange
        Iterator scenarioIteratorMock = mock(Iterator.class);
        ScenarioDetails scenarioMock = mock(ScenarioDetails.class);
        Queue<ScenarioDetails> scenariosMock = mock(Queue.class);
        ReportDetails reportDetailsMock = mock(ReportDetails.class);
        imageReport.setReportDetails(reportDetailsMock);
        when(reportDetailsMock.getNumberOfFailedTests()).thenReturn(1);
        when(reportDetailsMock.getSuiteName()).thenReturn("Anything");
        when(reportDetailsMock.getScenarios()).thenReturn(scenariosMock);
        when(scenariosMock.iterator()).thenReturn(scenarioIteratorMock);
        when(scenarioIteratorMock.hasNext()).thenReturn(true, false);
        when(scenarioIteratorMock.next()).thenReturn(scenarioMock);
        when(scenarioMock.getStatus()).thenReturn("FAILED");

        // Act
        imageReport.buildImageReport("title");

        // Assert

    }


}
