package com.ultimatesoftware.aeon.extensions.reporting.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

class TestCaseTests {

    private TestCase testCaseResult;

    @Mock
    private FailedExpectation feMock;

    @Mock
    private HighLevelStep hlsMock;

    @BeforeEach
    void setup() {
        testCaseResult = new TestCase();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetStatus_noDataStored_emptyString() {
        //Arrange

        //Act
        String data = testCaseResult.getStatus();

        //Assert
        assertEquals("", data);
    }

    @Test
    void setStatus_dataStored_retrievedMessage() {
        //Arrange
        String message = "Testing TestCaseResult status";
        testCaseResult.setStatus(message);

        //Act
        String data = testCaseResult.getStatus();

        //Assert
        assertEquals(message, data);
    }

    @Test
    void getPrefix_noDataStored_emptyString() {
        //Arrange

        //Act
        String data = testCaseResult.getPrefix();

        //Assert
        assertEquals("", data);
    }

    @Test
    void setPrefix_dataStored_retrievedMessage() {
        //Arrange
        String message = "Testing TestCaseResult prefix";
        testCaseResult.setPrefix(message);

        //Act
        String data = testCaseResult.getPrefix();

        //Assert
        assertEquals(message, data);
    }

    @Test
    void getDescription_noDataStored_emptyString() {
        //Arrange

        //Act
        String data = testCaseResult.getDescription();

        //Assert
        assertEquals("", data);
    }

    @Test
    void setDescription_dataStored_retrievedMessage() {
        //Arrange
        String message = "Testing TestCaseResult description";
        testCaseResult.setDescription(message);

        //Act
        String data = testCaseResult.getDescription();

        //Assert
        assertEquals(message, data);
    }

    @Test
    void getFailedExpectations_noDataStored_emptyList() {
        //Arrange

        //Act
        List<FailedExpectation> actual = testCaseResult.getFailedExpectations();

        //Assert
        assertEquals(Collections.emptyList(), actual);
    }

    @Test
    void setFailedExpectationsObject_listOfObjects_listWithData() {
        //Arrange
        List<FailedExpectation> expected = Arrays.asList(feMock, feMock, feMock);
        testCaseResult.setFailedExpectations(expected);

        //Act
        List<FailedExpectation> actual = testCaseResult.getFailedExpectations();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void setFailedExpectationsTestMessage_listOfObjectsWithMessageInfo_listWithData() {
        //Arrange
        when(feMock.getMessage()).thenReturn("My message");
        List<FailedExpectation> expected = Arrays.asList(feMock, feMock, feMock);
        testCaseResult.setFailedExpectations(expected);

        //Act
        List<FailedExpectation> actual = testCaseResult.getFailedExpectations();

        //Assert
        assertEquals(expected.get(0).getMessage(), actual.get(0).getMessage());
        assertEquals(expected.get(1).getMessage(), actual.get(1).getMessage());
        assertEquals(expected.get(2).getMessage(), actual.get(2).getMessage());
    }

    @Test
    void setFailedExpectationsTestStack_listOfObjectsWithStackInfo_listWithData() {
        //Arrange
        when(feMock.getStack()).thenReturn("My stack");
        List<FailedExpectation> expected = Arrays.asList(feMock, feMock, feMock);
        testCaseResult.setFailedExpectations(expected);

        //Act
        List<FailedExpectation> actual = testCaseResult.getFailedExpectations();

        //Assert
        assertEquals(expected.get(0).getStack(), actual.get(0).getStack());
        assertEquals(expected.get(1).getStack(), actual.get(1).getStack());
        assertEquals(expected.get(2).getStack(), actual.get(2).getStack());
    }

    @Test
    void getBrowserLogs_nothingSet_returnsNull() {
        //Arrange

        //Act
        List<Map<String, Object>> actual = testCaseResult.getBrowserLogs();

        //Assert
        assertNull(actual);
    }

    @Test
    void setBrowserLogs_listOfObjects_listWithData() {
        //Arrange
        List<Map<String, Object>> expected = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("foo", "bar");
        expected.add(map);
        testCaseResult.setBrowserLogs(expected);

        //Act
        List<Map<String, Object>> actual = testCaseResult.getBrowserLogs();

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    void getStarted_noDataStored_emptyString() {
        //Arrange

        //Act
        String data = testCaseResult.getStarted();

        //Assert
        assertEquals("", data);
    }

    @Test
    void setStarted_dataStored_retrievedMessage() {
        //Arrange
        String message = "Testing TestCaseResult Started";
        testCaseResult.setStarted(message);

        //Act
        String data = testCaseResult.getStarted();

        //Assert
        assertEquals(message, data);
    }

    @Test
    void getStopped_noDataStored_emptyString() {
        //Arrange

        //Act
        String data = testCaseResult.getStopped();

        //Assert
        assertEquals("", data);
    }

    @Test
    void setStopped_dataStored_retrievedMessage() {
        //Arrange
        String message = "Testing TestCaseResult Stopped";
        testCaseResult.setStopped(message);

        //Act
        String data = testCaseResult.getStopped();

        //Assert
        assertEquals(message, data);
    }

    @Test
    void getDuration_noDataStored_emptyString() {
        //Arrange

        //Act
        String data = testCaseResult.getDuration();

        //Assert
        assertEquals("", data);
    }

    @Test
    void setDuration_dataStored_retrievedMessage() {
        //Arrange
        String message = "Testing TestCaseResult Duration";
        testCaseResult.setDuration(message);

        //Act
        String data = testCaseResult.getDuration();

        //Assert
        assertEquals(message, data);
    }

    @Test
    void getScreenshotPath_noDataStored_emptyString() {
        //Arrange

        //Act
        String data = testCaseResult.getScreenshotPath();

        //Assert
        assertEquals("", data);
    }

    @Test
    void setScreenshotPath_dataStored_retrievedMessage() {
        //Arrange
        String message = "Testing TestCaseResult ScreenshotPath";
        testCaseResult.setScreenshotPath(message);

        //Act
        String data = testCaseResult.getScreenshotPath();

        //Assert
        assertEquals(message, data);
    }

    @Test
    void getVideoUrl_noDataStored_emptyString() {
        //Arrange

        //Act
        String data = testCaseResult.getVideoUrl();

        //Assert
        assertEquals("", data);
    }

    @Test
    void setVideoUrl_dataStored_retrievedMessage() {
        //Arrange
        String message = "Testing TestCaseResult VideoUrl";
        testCaseResult.setVideoUrl(message);

        //Act
        String data = testCaseResult.getVideoUrl();

        //Assert
        assertEquals(message, data);
    }

    @Test
    void getStepsObject_noDataAdded_initializedWithEmptyHighLevelStep() {
        //Arrange

        //Act
        List<HighLevelStep> actual = testCaseResult.getSteps();

        //Assert
        assertEquals(1, actual.size());
        assertEquals("", actual.get(0).getName());
        assertEquals(0, actual.get(0).getSteps().size());
    }

    @Test
    void setStepsTestGetName_dataStoredForName_listOfObjectsWithNameProperty() {
        //Arrange
        when(hlsMock.getName()).thenReturn("My name");
        List<HighLevelStep> expected = Arrays.asList(hlsMock, hlsMock, hlsMock);
        testCaseResult.setSteps(expected);

        //Act
        List<HighLevelStep> actual = testCaseResult.getSteps();

        //Assert
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(1).getName(), actual.get(1).getName());
        assertEquals(expected.get(2).getName(), actual.get(2).getName());
    }

    @Test
    void setStepsTestGetSteps_dataStoredForSteps_listOfObjectsWithStepsProperty() {
        //Arrange
        when(hlsMock.getSteps()).thenReturn(Arrays.asList("First", "Second", "Third"));
        List<HighLevelStep> expected = Arrays.asList(hlsMock, hlsMock, hlsMock);
        testCaseResult.setSteps(expected);

        //Act
        List<HighLevelStep> actual = testCaseResult.getSteps();

        //Assert
        assertEquals(expected.get(0).getSteps(), actual.get(0).getSteps());
        assertEquals(expected.get(1).getSteps(), actual.get(1).getSteps());
        assertEquals(expected.get(2).getSteps(), actual.get(2).getSteps());
    }

    @Test
    void threadIdTest() {
        // Arrange

        // Act
        testCaseResult.setThreadId(200);

        // Assert
        assertEquals(200, testCaseResult.getThreadId());
    }

    @Test
    void startTimeTest() {
        // Arrange

        // Act
        testCaseResult.setStartTime(2000);

        // Assert
        assertEquals(2000, testCaseResult.getStartTime());
    }

    @Test
    void stackTraceTest() {
        // Arrange

        // Act
        testCaseResult.setError("error message", "line1\nline2");

        // Assert
        assertEquals("error message", testCaseResult.getFailedExpectations().get(0).getMessage());
        assertEquals("line1\nline2", testCaseResult.getFailedExpectations().get(0).getStack());
    }

    @Test
    void stepsTest() {
        // Arrange
        String highLevel1 = "BIG STEP 1";
        String highLevel2 = "BIG STEP 2";
        String lowLevel1 = "SMALL STEP 1";
        String lowLevel2 = "SMALL STEP 2";

        List<HighLevelStep> expectedSteps = new ArrayList<>();
        List<String> valuesList0 = new ArrayList<>();
        valuesList0.add("these are your first steps");
        List<String> valuesList1 = new ArrayList<>();
        valuesList1.add(lowLevel1 + ".1");
        valuesList1.add(lowLevel2 + ".1");
        List<String> valuesList2 = new ArrayList<>();
        valuesList2.add(lowLevel1 + ".2");
        valuesList2.add(lowLevel2 + ".2");
        expectedSteps.add(new HighLevelStep("", valuesList0));
        expectedSteps.add(new HighLevelStep(highLevel1, valuesList1));
        expectedSteps.add(new HighLevelStep(highLevel2, valuesList2));

        // Act
        testCaseResult.addStep("these are your first steps");
        testCaseResult.addHighLevelStep(highLevel1);
        testCaseResult.addStep(lowLevel1 + ".1");
        testCaseResult.addStep(lowLevel2 + ".1");
        testCaseResult.addHighLevelStep(highLevel2);
        testCaseResult.addStep(lowLevel1 + ".2");
        testCaseResult.addStep(lowLevel2 + ".2");
        List<HighLevelStep> resultSteps = testCaseResult.getSteps();

        // Assert
        assertEquals(expectedSteps.size(), resultSteps.size());
        for (int i = 0; i < expectedSteps.size(); i++) {
            HighLevelStep expectedStep = expectedSteps.get(i);
            HighLevelStep resultStep = resultSteps.get(i);

            assertEquals(expectedStep.getName(), resultStep.getName());
            assertEquals(expectedStep.getSteps().size(), resultStep.getSteps().size());
            for (int j = 0; j < expectedStep.getSteps().size(); j++) {
                assertEquals(expectedStep.getSteps().get(j), resultStep.getSteps().get(j));
            }
        }
    }

    @Test
    void screenshotTest() {
        // Arrange

        // Act
        testCaseResult.setScreenshotPath("screenshot");

        // Assert
        assertEquals("screenshot", testCaseResult.getScreenshotPath());
    }

    @Test
    void videoUrlTest() {
        // Arrange

        // Act
        testCaseResult.setVideoUrl("videoUrl");

        // Assert
        assertEquals("videoUrl", testCaseResult.getVideoUrl());
    }

    @Test
    void scenarioErrorMessageTest() {
        // Arrange

        // Act
        testCaseResult.setError("out of bounds", null);

        // Assert
        assertEquals("out of bounds", testCaseResult.getFailedExpectations().get(0).getMessage());
    }

    @Test
    void getShortenedErrorMessage_messageLengthUnderTheLimit_returnsFullMessage() {
        // Arrange
        testCaseResult.setError("message with &, < and > characters", null);

        // Act
        String message = testCaseResult.getShortenedErrorMessage(200);

        // Assert
        assertEquals("message with &amp;, &lt; and &gt; characters", message);
    }

    @Test
    void getShortenedErrorMessage_messageLengthOverTheLimit_returnsShortenedErrorMessage() {
        // Arrange
        testCaseResult.setError("message with &, < and > characters over the limit", null);

        // Act
        String message = testCaseResult.getShortenedErrorMessage(55);

        // Assert
        assertEquals("message with &amp;, &lt; and &gt; characters over the l...", message);
    }

    @Test
    void shortenedErrorMessage_whenNoFailedExpectations_returnsEmptyString() {
        // Arrange

        // Act
        String message = testCaseResult.getShortenedErrorMessage(55);

        // Assert
        assertEquals("", message);
    }
}
