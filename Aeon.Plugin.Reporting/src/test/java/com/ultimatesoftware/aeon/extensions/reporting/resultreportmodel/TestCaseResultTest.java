package com.ultimatesoftware.aeon.extensions.reporting.resultreportmodel;

import com.ultimatesoftware.aeon.extensions.reporting.models.HighLevelStep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class TestCaseResultTest {

    private TestCaseResult testCaseResult;

    @Mock
    private FailedExpectation feMock;

    @Mock
    private HighLevelStep hlsMock;

    @BeforeEach
    void setup() {
        testCaseResult = new TestCaseResult();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getStatus_noDataStored_nullString() {
        //Arrange

        //Act
        String data = testCaseResult.getStatus();

        //Assert
        assertEquals(null, data);
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
    void getPrefix_noDataStored_nullString() {
        //Arrange

        //Act
        String data = testCaseResult.getPrefix();

        //Assert
        assertEquals(null, data);
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
    void getDescription_noDataStored_nullString() {
        //Arrange

        //Act
        String data = testCaseResult.getDescription();

        //Assert
        assertEquals(null, data);
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
    void getBrowserLogs_listOfObjects_emptyArray() {
        //Arrange
        List<Map<String, Object>> expected = new ArrayList<>();

        //Act
        List<Map<String, Object>> actual = testCaseResult.getBrowserLogs();

        //Assert
        assertEquals(expected, actual);
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
    void getStarted_noDataStored_nullString() {
        //Arrange

        //Act
        String data = testCaseResult.getStarted();

        //Assert
        assertEquals(null, data);
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
    void getStopped_noDataStored_nullString() {
        //Arrange

        //Act
        String data = testCaseResult.getStopped();

        //Assert
        assertEquals(null, data);
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
    void getDuration_noDataStored_nullString() {
        //Arrange

        //Act
        String data = testCaseResult.getDuration();

        //Assert
        assertEquals(null, data);
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
    void getScreenshotPath_noDataStored_nullString() {
        //Arrange

        //Act
        String data = testCaseResult.getScreenshotPath();

        //Assert
        assertEquals(null, data);
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
    void getVideoUrl_noDataStored_nullString() {
        //Arrange

        //Act
        String data = testCaseResult.getVideoUrl();

        //Assert
        assertEquals(null, data);
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
    void getStepsObject_noDataStored_nullObject() {
        //Arrange

        //Act
        List<HighLevelStep> actual = testCaseResult.getSteps();

        //Assert
        assertEquals(null, actual);
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
}
