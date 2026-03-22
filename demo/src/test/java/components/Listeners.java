package components;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.common.Constant;
import com.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

    ExtentReports extentReporter = ExtentReporterNG.getReportObject();
    ExtentTest test;
    ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<ExtentTest>(); // ThreadLocal to handle parallel

    @Override
    public void onTestFailure(ITestResult result) {
        extentTestThreadLocal.get().log(Status.FAIL, "Test case failed");
        Throwable throwable = result.getThrowable();
        if (throwable != null) {
            extentTestThreadLocal.get().log(Status.FAIL, "Failure details: " + throwable);
        }
        try {
            // WebDriver driver = (WebDriver)
            // result.getTestClass().getRealClass().getField("driver")
            // .get(result.getInstance());
            WebDriver driver = Constant.WEBDRIVER;
            String filePath = getScreenShot(result.getMethod().getMethodName(), driver);
            extentTestThreadLocal.get().addScreenCaptureFromPath(filePath,
                    getScreenShot(result.getMethod().getMethodName(), driver));
        } catch (Exception e) {
            extentTestThreadLocal.get().log(Status.FAIL, "Screenshot capture failed: " + e.getMessage());
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
