package Utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class CaptureScreenshot {

    /**
     * Captures a screenshot and saves it under the /screenshots directory
     *
     * @param driver    The WebDriver instance
     * @param testName  The name of the test (used in filename)
     * @return The full path to the screenshot file, or null if driver is null
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            System.out.println("[ERROR] WebDriver is null. Cannot take screenshot for test: " + testName);
            return null;
        }

        // Create timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // Define screenshot directory and file path
        String screenshotDir = System.getProperty("user.dir") + "/screenshots/";
        String screenshotPath = screenshotDir + testName + "_" + timestamp + ".png";

        // Ensure the directory exists
        File dir = new File(screenshotDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Take screenshot
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File dest = new File(screenshotPath);
        try {
            FileHandler.copy(src, dest);
            System.out.println("[INFO] Screenshot saved: " + screenshotPath);
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to save screenshot: " + e.getMessage());
            e.printStackTrace();
        }

        return screenshotPath;
    }
}
