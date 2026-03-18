package components;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

    ExtentReports extentReporter = ExtentReporterNG.getReportObject();
    ExtentTest test;
    ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<ExtentTest>(); // ThreadLocal to handle parallel

    @Override
    public void onTestFailure(ITestResult result) {
        extentTestThreadLocal.get().log(Status.FAIL, "Test case failed");
        try {
            driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
            String filePath = getScreenShot(result.getMethod().getMethodName(), driver);
            extentTestThreadLocal.get().addScreenCaptureFromPath(filePath,
                    getScreenShot(result.getMethod().getMethodName(), driver));
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extentReporter.createTest(result.getMethod().getMethodName());
        extentTestThreadLocal.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTestThreadLocal.get().log(Status.PASS, "Test case passed");
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReporter.flush();
    }

}
