package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import Utilities.CaptureScreenshot;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

    @Test(groups= {"Sanity", "Master"})
    public void verify_login() {
        logger.info("***** TC002_LoginTest: Login Test Started *****");

        try {
            HomePage hp = new HomePage(driver);
            hp.clickLogin(); // No need for clickRegister before login

            logger.info("Clicked on Login link");

            LoginPage lp = new LoginPage(driver);
            lp.setEmail(p.getProperty("email"));
            lp.setPassword(p.getProperty("password"));
            logger.info("Entered email and password");

            lp.clickLogin();
            logger.info("Clicked Login button");

            MyAccountPage macc = new MyAccountPage(driver);

            boolean targetPage = macc.isMyAccountPageExists();

            logger.info("Current URL: " + driver.getCurrentUrl());
            logger.info("Page Title: " + driver.getTitle());

            if (targetPage) {
                logger.info("Login Test Passed: My Account page found");
                Assert.assertTrue(true);
            } else {
                logger.error("Login Test Failed: My Account page was not found");
                String screenshotPath = CaptureScreenshot.takeScreenshot(driver, "verify_login");
                logger.error("Screenshot captured at: " + screenshotPath);
                Assert.fail("Login failed: My Account page was not found.");
            }

        } catch (Exception e) {
            logger.error("Exception during login test: " + e.getMessage());
            String screenshotPath = CaptureScreenshot.takeScreenshot(driver, "verify_login_exception");
            logger.error("Screenshot captured at: " + screenshotPath);
            Assert.fail("Exception in login test"); 
        }

        logger.info("***** TC002_LoginTest: Login Test Finished *****");
    }
}
