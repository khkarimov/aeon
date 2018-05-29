package aeon.extensions.reporting;

import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.AeonTestExecution;
import org.testng.*;

public class TestNgListener implements ITestListener, ISuiteListener {

    public void onTestStart(ITestResult iTestResult) {
        AeonTestExecution.startTest(iTestResult.getName() + "." + iTestResult.getInstanceName(), (String[]) null);
    }

    public void onTestSuccess(ITestResult iTestResult) {
        AeonTestExecution.testSucceeded();
    }

    public void onTestFailure(ITestResult iTestResult) {
        AeonTestExecution.testFailed(iTestResult.getThrowable().getMessage());
    }

    public void onTestSkipped(ITestResult iTestResult) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        // Not needed
    }

    public void onStart(ITestContext iTestContext) {
        // Not needed
    }

    public void onFinish(ITestContext iTestContext) {
        // Not needed
    }

    public void onStart(ISuite iSuite) {
        ReportingPlugin.suiteName = iSuite.getName();
    }

    public void onFinish(ISuite iSuite) {
        Aeon.done();
    }
}
