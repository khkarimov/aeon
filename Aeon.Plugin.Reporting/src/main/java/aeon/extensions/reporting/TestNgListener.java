package aeon.extensions.reporting;

import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.AeonTestExecution;
import org.testng.*;

/**
 * TestNG Listener for Aeon.
 *
 * Calls aeon test execution methods for certain test events.
 */
public class TestNgListener implements ITestListener, ISuiteListener {

    @Override
    public void onTestStart(ITestResult testResult) {
        AeonTestExecution.startTest(testResult.getInstanceName() + "." + testResult.getName(), (String[]) null);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        AeonTestExecution.testSucceeded();
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        AeonTestExecution.testFailed(iTestResult.getThrowable().getMessage(), iTestResult.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        AeonTestExecution.testSkipped(iTestResult.getInstanceName() + "." + iTestResult.getName(), (String[]) null);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        // Not needed
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        // Not needed
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        // Not needed
    }

    @Override
    public void onStart(ISuite iSuite) {
        AeonTestExecution.beforeStart(iSuite.getName());
    }

    @Override
    public void onFinish(ISuite iSuite) {
        Aeon.done();
    }
}
