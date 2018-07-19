import aeon.extensions.reporting.Scenario;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScenarioTests {

    @Test
    public void scenarioNameTest() {
        Scenario scenario = new Scenario();
        scenario.setScenarioName("scenarioName");

        Assert.assertEquals(scenario.getScenarioName(), "scenarioName");
    }

    @Test
    public void startTimeTest() {
        Scenario scenario = new Scenario();
        scenario.setStartTime(2000);

        Assert.assertEquals(scenario.getStartTime(), 2000);
    }

    @Test
    public void endTimeTest() {
        Scenario scenario = new Scenario();
        scenario.setEndTime(2000);

        Assert.assertEquals(scenario.getEndTime(), 2000);
    }

    @Test
    public void stackTraceTest() {
        Scenario scenario = new Scenario();
        scenario.setStackTrace("line1\nline2");

        Assert.assertEquals(scenario.getStackTrace(), "line1\nline2");
    }

    @Test
    public void screenshotTest() {
        Scenario scenario = new Scenario();
        Image image = new BufferedImage(2, 2, BufferedImage.TYPE_BYTE_BINARY);
        scenario.setScreenshot(image);

        Assert.assertEquals(scenario.getScreenshot(), image);
    }

    @Test
    public void scenarioStatusTest() {
        Scenario scenario = new Scenario();
        scenario.setStatus("FAILED");

        Assert.assertEquals(scenario.getStatus(), "FAILED");
    }

    @Test
    public void scenarioErrorMessageTest() {
        Scenario scenario = new Scenario();
        scenario.setErrorMessage("out of bounds");

        Assert.assertEquals(scenario.getErrorMessage(), "out of bounds");
    }

    @Test
    public void shortenedErrorMessageTestUnderTheLimit() {
        Scenario scenario = new Scenario();
        scenario.setErrorMessage("message with &, < and > characters");

        Assert.assertEquals(scenario.getShortenedErrorMessage(200),
                "message with &amp;, &lt; and &gt; characters");
    }

    @Test
    public void shortenedErrorMessageTestOverTheLimit() {
        Scenario scenario = new Scenario();
        scenario.setErrorMessage("message with &, < and > characters over the limit");

        Assert.assertEquals(scenario.getShortenedErrorMessage(55),
                "message with &amp;, &lt; and &gt; characters over the l...");
    }

    @Test
    public void scenarioModuleNameTest() {
        Scenario scenario = new Scenario();
        scenario.setModuleName("moduleName");

        Assert.assertEquals(scenario.getModuleName(), "moduleName");
    }
}
