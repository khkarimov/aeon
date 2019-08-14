package com.ultimatesoftware.aeon.extensions.testng;

import com.ultimatesoftware.aeon.core.testabstraction.product.Aeon;
import com.ultimatesoftware.aeon.core.testabstraction.product.AeonTestExecution;
import org.testng.*;

/**
 * TestNG Listener for Aeon.
 * <p>
 * Calls aeon test execution methods for certain test events.
 */
public class TestNgListener implements ITestListener, ISuiteListener {

    @Override
    public void onTestStart(ITestResult testResult) {
        AeonTestExecution.startTest(testResult.getInstanceName() + "." + testResult.getName(), (String[]) null);
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {
        AeonTestExecution.testSucceeded();
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        AeonTestExecution.testFailed(testResult.getThrowable().getMessage(), testResult.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult testResult) {
        AeonTestExecution.testSkipped(testResult.getInstanceName() + "." + testResult.getName(), (String[]) null);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {
        // Not needed
    }

    @Override
    public void onStart(ITestContext testContext) {
        // Not needed
    }

    @Override
    public void onFinish(ITestContext testContext) {
        // Not needed
    }

    @Override
    public void onStart(ISuite suite) {
        AeonTestExecution.beforeStart(suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        Aeon.done();
    }
}
