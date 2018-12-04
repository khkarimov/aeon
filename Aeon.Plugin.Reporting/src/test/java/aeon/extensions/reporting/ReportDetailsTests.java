package aeon.extensions.reporting;

import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ReportDetailsTests {

    @Test
    public void suiteNameTest() {
        ReportDetails reportDetails = new ReportDetails();
        reportDetails.setSuiteName("suiteName");

        Assert.assertEquals(reportDetails.getSuiteName(), "suiteName");
    }

    @Test
    public void startTimeTest() {
        ReportDetails reportDetails = new ReportDetails();
        reportDetails.setStartTime(200);

        Assert.assertEquals(reportDetails.getStartTime(), 200);
    }

    @Test
    public void endTimeTest() {
        ReportDetails reportDetails = new ReportDetails();
        reportDetails.setEndTime(200);

        Assert.assertEquals(reportDetails.getEndTime(), 200);
    }

    @Test
    public void totalTimeTest() {
        ReportDetails reportDetails = new ReportDetails();
        reportDetails.setStartTime(300);
        reportDetails.setEndTime(500);

        Assert.assertEquals(reportDetails.getTotalTime(), 200);
    }

    @Test
    public void correlationIdTest() {
        ReportDetails reportDetails = new ReportDetails();
        reportDetails.setCorrelationId("correlationId");

        Assert.assertEquals(reportDetails.getCorrelationId(), "correlationId");
    }

    @Test
    public void scenariosTest() {
        ReportDetails reportDetails = new ReportDetails();

        Queue<ScenarioDetails> scenarios = new ConcurrentLinkedQueue<>();
        ScenarioDetails passedScenario = new ScenarioDetails();
        passedScenario.setStatus("PASSED");
        scenarios.add(passedScenario);
        ScenarioDetails failedScenario = new ScenarioDetails();
        failedScenario.setStatus("FAILED");
        scenarios.add(failedScenario);
        ScenarioDetails skippedScenario = new ScenarioDetails();
        skippedScenario.setStatus("SKIPPED");
        scenarios.add(skippedScenario);
        reportDetails.setScenarios(scenarios);

        Assert.assertEquals(reportDetails.getScenarios(), scenarios);

        Assert.assertEquals(reportDetails.getTotalNumberOfTests(), 3);
    }

    @Test
    public void passedScenariosTest() {
        ReportDetails reportDetails = new ReportDetails();

        Queue<ScenarioDetails> scenarios = new ConcurrentLinkedQueue<>();
        ScenarioDetails passedScenario = new ScenarioDetails();
        passedScenario.setStatus("PASSED");
        scenarios.add(passedScenario);
        ScenarioDetails passedScenario2 = new ScenarioDetails();
        passedScenario2.setStatus("PASSED");
        scenarios.add(passedScenario2);
        ScenarioDetails passedScenario3 = new ScenarioDetails();
        passedScenario3.setStatus("PASSED");
        scenarios.add(passedScenario3);
        ScenarioDetails failedScenario = new ScenarioDetails();
        failedScenario.setStatus("FAILED");
        scenarios.add(failedScenario);
        ScenarioDetails skippedScenario = new ScenarioDetails();
        skippedScenario.setStatus("SKIPPED");
        scenarios.add(skippedScenario);
        reportDetails.setScenarios(scenarios);

        Assert.assertEquals(reportDetails.getScenarios(), scenarios);

        Assert.assertEquals(reportDetails.getNumberOfPassedTests(), 3);
        Assert.assertEquals(reportDetails.getTotalNumberOfTests(), 5);
    }

    @Test
    public void failedScenariosTest() {
        ReportDetails reportDetails = new ReportDetails();

        Queue<ScenarioDetails> scenarios = new ConcurrentLinkedQueue<>();
        ScenarioDetails passedScenario = new ScenarioDetails();
        passedScenario.setStatus("PASSED");
        scenarios.add(passedScenario);
        ScenarioDetails failedScenario = new ScenarioDetails();
        failedScenario.setStatus("FAILED");
        scenarios.add(failedScenario);
        ScenarioDetails failedScenario2 = new ScenarioDetails();
        failedScenario2.setStatus("FAILED");
        scenarios.add(failedScenario2);
        ScenarioDetails failedScenario3 = new ScenarioDetails();
        failedScenario3.setStatus("FAILED");
        scenarios.add(failedScenario3);
        ScenarioDetails skippedScenario = new ScenarioDetails();
        skippedScenario.setStatus("SKIPPED");
        scenarios.add(skippedScenario);
        reportDetails.setScenarios(scenarios);

        Assert.assertEquals(reportDetails.getScenarios(), scenarios);

        Assert.assertEquals(reportDetails.getNumberOfFailedTests(), 3);
        Assert.assertEquals(reportDetails.getTotalNumberOfTests(), 5);
    }

    @Test
    public void skippedScenariosTest() {
        ReportDetails reportDetails = new ReportDetails();

        Queue<ScenarioDetails> scenarios = new ConcurrentLinkedQueue<>();
        ScenarioDetails passedScenario = new ScenarioDetails();
        passedScenario.setStatus("PASSED");
        scenarios.add(passedScenario);
        ScenarioDetails failedScenario = new ScenarioDetails();
        failedScenario.setStatus("FAILED");
        scenarios.add(failedScenario);
        ScenarioDetails skippedScenario = new ScenarioDetails();
        skippedScenario.setStatus("SKIPPED");
        scenarios.add(skippedScenario);
        ScenarioDetails skippedScenario2 = new ScenarioDetails();
        skippedScenario2.setStatus("SKIPPED");
        scenarios.add(skippedScenario2);
        ScenarioDetails skippedScenario3 = new ScenarioDetails();
        skippedScenario3.setStatus("SKIPPED");
        scenarios.add(skippedScenario3);
        reportDetails.setScenarios(scenarios);

        Assert.assertEquals(reportDetails.getScenarios(), scenarios);

        Assert.assertEquals(reportDetails.getNumberOfSkippedTests(), 3);
        Assert.assertEquals(reportDetails.getTotalNumberOfTests(), 5);
    }
}
