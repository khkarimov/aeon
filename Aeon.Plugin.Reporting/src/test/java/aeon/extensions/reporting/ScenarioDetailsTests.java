package aeon.extensions.reporting;

import aeon.extensions.reporting.models.HighLevelStep;
import aeon.extensions.reporting.models.ScenarioDetails;
import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ScenarioDetailsTests {

    @Test
    public void threadIdTest() {
        // Arrange
        ScenarioDetails scenario = new ScenarioDetails();

        // Act
        scenario.setThreadId(200);

        // Assert
        assertEquals(scenario.getThreadId(), 200);
    }

    @Test
    public void scenarioNameTest() {
        // Arrange
        ScenarioDetails scenario = new ScenarioDetails();

        // Act
        scenario.setTestName("scenarioName");

        // Assert
        assertEquals(scenario.getTestName(), "scenarioName");
    }

    @Test
    public void startTimeTest() {
        // Arrange
        ScenarioDetails scenario = new ScenarioDetails();

        // Act
        scenario.setStartTime(2000);

        // Assert
        assertEquals(scenario.getStartTime(), 2000);
    }

    @Test
    public void endTimeTest() {
        // Arrange
        ScenarioDetails scenario = new ScenarioDetails();

        // Act
        scenario.setEndTime(2000);

        // Assert
        assertEquals(scenario.getEndTime(), 2000);
    }

    @Test
    public void stackTraceTest() {
        // Arrange
        ScenarioDetails scenario = new ScenarioDetails();

        // Act
        scenario.setStackTrace("line1\nline2");

        // Assert
        assertEquals(scenario.getStackTrace(), "line1\nline2");
    }

    @Test
    public void stepsTest() {
        // Arrange
        String highLevel1 = "BIG STEP 1";
        String highLevel2 = "BIG STEP 2";
        String lowLevel1 = "SMOL STEP 1";
        String lowLevel2 = "SMOL STEP 2";

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
        ScenarioDetails scenario = new ScenarioDetails();
        scenario.addStep("these are your first steps");
        scenario.addHighLevelStep(highLevel1);
        scenario.addStep(lowLevel1 + ".1");
        scenario.addStep(lowLevel2 + ".1");
        scenario.addHighLevelStep(highLevel2);
        scenario.addStep(lowLevel1 + ".2");
        scenario.addStep(lowLevel2 + ".2");
        List<HighLevelStep> resultSteps = scenario.getSteps();

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
    public void screenshotTest() {
        // Arrange
        ScenarioDetails scenario = new ScenarioDetails();
        Image image = new BufferedImage(2, 2, BufferedImage.TYPE_BYTE_BINARY);

        // Act
        scenario.setScreenshot(image);

        // Assert
        assertEquals(scenario.getScreenshot(), image);
    }

    @Test
    public void videoUrlTest() {
        // Arrange
        ScenarioDetails scenario = new ScenarioDetails();

        // Act
        scenario.setVideoUrl("videoUrl");

        // Assert
        assertEquals(scenario.getVideoUrl(), "videoUrl");
    }

    @Test
    public void scenarioStatusTest() {
        // Arrange
        ScenarioDetails scenario = new ScenarioDetails();

        // Act
        scenario.setStatus("FAILED");

        // Assert
        assertEquals(scenario.getStatus(), "FAILED");
    }

    @Test
    public void scenarioErrorMessageTest() {
        // Arrange
        ScenarioDetails scenario = new ScenarioDetails();

        // Act
        scenario.setErrorMessage("out of bounds");

        // Assert
        assertEquals(scenario.getErrorMessage(), "out of bounds");
    }

    @Test
    public void shortenedErrorMessageTestUnderTheLimit() {
        // Arrange
        ScenarioDetails scenario = new ScenarioDetails();

        // Act
        scenario.setErrorMessage("message with &, < and > characters");

        // Assert
        assertEquals(scenario.getShortenedErrorMessage(200),
                "message with &amp;, &lt; and &gt; characters");
    }

    @Test
    public void shortenedErrorMessageTestOverTheLimit() {
        // Arrange
        ScenarioDetails scenario = new ScenarioDetails();

        // Act
        scenario.setErrorMessage("message with &, < and > characters over the limit");

        // Assert
        assertEquals(scenario.getShortenedErrorMessage(55),
                "message with &amp;, &lt; and &gt; characters over the l...");
    }

    @Test
    public void scenarioClassNameTest() {
        // Arrange
        ScenarioDetails scenario = new ScenarioDetails();

        // Act
        scenario.setClassName("moduleName");

        // Assert
        assertEquals(scenario.getClassName(), "moduleName");
    }
}
