package Utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager extends BaseClass implements ITestListener {

    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);

    public ExtentReports extent;
    public ExtentTest test;
    public ExtentSparkReporter sparkReporter;
    String repName;

    @Override
    public void onStart(ITestContext testContext) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        repName = "TestReport_" + timestamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);

        sparkReporter.config().setDocumentTitle("Nopcommerce Automation Report");
        sparkReporter.config().setReportName("Nopcommerce Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Nopcommerce");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os = testContext.getCurrentXmlTest().getParameter("os");
        String browser = testContext.getCurrentXmlTest().getParameter("browser");

        if (os != null) extent.setSystemInfo("Operating System", os);
        if (browser != null) extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.INFO, result.getName() + " started execution");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, result.getName() + " got successfully executed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        String screenshotPath = (driver != null)
                ? CaptureScreenshot.takeScreenshot(driver, testName)
                : "[Driver is null - screenshot skipped]";

        test.fail("Test Failed: " + result.getThrowable());

        try {
            if (!screenshotPath.contains("skipped")) {
                test.addScreenCaptureFromPath(screenshotPath, testName);
            }
        } catch (Exception e) {
            logger.error("Failed to attach screenshot to report", e);
        }

        logger.error("Test Failed: " + testName);
        logger.error("Screenshot info: " + screenshotPath);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName() + " got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }

    @Override
    public void onFinish(ITestContext testContext) {
        extent.flush();

        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            if (extentReport.exists()) {
                Desktop.getDesktop().browse(extentReport.toURI());
            } else {
                System.out.println("Report not found: " + pathOfExtentReport);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
