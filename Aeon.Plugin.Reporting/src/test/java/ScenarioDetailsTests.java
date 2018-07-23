import aeon.extensions.reporting.ScenarioDetails;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScenarioDetailsTests {

    @Test
    public void scenarioNameTest() {
        ScenarioDetails scenario = new ScenarioDetails();
        scenario.setTestName("scenarioName");

        Assert.assertEquals(scenario.getTestName(), "scenarioName");
    }

    @Test
    public void startTimeTest() {
        ScenarioDetails scenario = new ScenarioDetails();
        scenario.setStartTime(2000);

        Assert.assertEquals(scenario.getStartTime(), 2000);
    }

    @Test
    public void endTimeTest() {
        ScenarioDetails scenario = new ScenarioDetails();
        scenario.setEndTime(2000);

        Assert.assertEquals(scenario.getEndTime(), 2000);
    }

    @Test
    public void stackTraceTest() {
        ScenarioDetails scenario = new ScenarioDetails();
        scenario.setStackTrace("line1\nline2");

        Assert.assertEquals(scenario.getStackTrace(), "line1\nline2");
    }

    @Test
    public void screenshotTest() {
        ScenarioDetails scenario = new ScenarioDetails();
        Image image = new BufferedImage(2, 2, BufferedImage.TYPE_BYTE_BINARY);
        scenario.setScreenshot(image);

        Assert.assertEquals(scenario.getScreenshot(), image);
    }

    @Test
    public void scenarioStatusTest() {
        ScenarioDetails scenario = new ScenarioDetails();
        scenario.setStatus("FAILED");

        Assert.assertEquals(scenario.getStatus(), "FAILED");
    }

    @Test
    public void scenarioErrorMessageTest() {
        ScenarioDetails scenario = new ScenarioDetails();
        scenario.setErrorMessage("out of bounds");

        Assert.assertEquals(scenario.getErrorMessage(), "out of bounds");
    }

    @Test
    public void shortenedErrorMessageTestUnderTheLimit() {
        ScenarioDetails scenario = new ScenarioDetails();
        scenario.setErrorMessage("message with &, < and > characters");

        Assert.assertEquals(scenario.getShortenedErrorMessage(200),
                "message with &amp;, &lt; and &gt; characters");
    }

    @Test
    public void shortenedErrorMessageTestOverTheLimit() {
        ScenarioDetails scenario = new ScenarioDetails();
        scenario.setErrorMessage("message with &, < and > characters over the limit");

        Assert.assertEquals(scenario.getShortenedErrorMessage(55),
                "message with &amp;, &lt; and &gt; characters over the l...");
    }

    @Test
    public void scenarioClassNameTest() {
        ScenarioDetails scenario = new ScenarioDetails();
        scenario.setClassName("moduleName");

        Assert.assertEquals(scenario.getClassName(), "moduleName");
    }
}
