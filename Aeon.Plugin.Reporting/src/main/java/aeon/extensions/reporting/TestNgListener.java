package aeon.extensions.reporting;

import aeon.core.testabstraction.product.Aeon;
import aeon.core.testabstraction.product.AeonTestExecution;
import org.testng.*;

public class TestNgListener implements ITestListener, ISuiteListener {

    public void onTestStart(ITestResult iTestResult) {
        AeonTestExecution.startTest(iTestResult.getInstanceName() + "." + iTestResult.getName(), (String[]) null);
    }

    public void onTestSuccess(ITestResult iTestResult) {
        AeonTestExecution.testSucceeded();
    }

    public void onTestFailure(ITestResult iTestResult) {
        AeonTestExecution.testFailed(iTestResult.getThrowable().getMessage(), iTestResult.getThrowable());
    }

    public void onTestSkipped(ITestResult iTestResult) {
        AeonTestExecution.testSkipped();
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
        AeonTestExecution.beforeStart(iSuite.getName());
    }

    public void onFinish(ISuite iSuite) {
        Aeon.done();
    }
}
