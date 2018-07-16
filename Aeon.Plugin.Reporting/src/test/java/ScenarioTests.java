import aeon.extensions.reporting.Scenario;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

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
    public void scenarioModuleNameTest() {
        Scenario scenario = new Scenario();
        scenario.setModuleName("moduleName");

        Assert.assertEquals(scenario.getModuleName(), "moduleName");
    }
}
