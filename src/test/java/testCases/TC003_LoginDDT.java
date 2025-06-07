package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import Utilities.DataProviders;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC003_LoginDDT extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups="Datadriven")
    public void verify_LoginDDT(String email, String pwd, String exp) {
        logger.info("**** Starting TC003_LoginDDT with data - Email: {}, Password: {}, Expected: {}", 
                   email, pwd, exp);

        try {
            HomePage hp = new HomePage(driver);
            hp.clickLogin();
            logger.info("Clicked on Login link");

            LoginPage lp = new LoginPage(driver);
            logger.info("Entering credentials");
            lp.setEmail(email);
            lp.setPassword(pwd);
            lp.clickLogin();
            logger.info("Submitted login form");

            MyAccountPage macc = new MyAccountPage(driver);
            boolean targetPage = macc.isMyAccountPageExists();

            if (exp.equalsIgnoreCase("Valid")) {
                if (targetPage) {
                    logger.info("Login successful for valid credentials");
                    Assert.assertTrue(true, "Valid login should redirect to My Account page");
                    macc.clickLogout();

                } else {
                    logger.error("Login failed for valid credentials");
                    Assert.fail("Valid login failed - not redirected to My Account page");
                }
            } else if (exp.equalsIgnoreCase("Invalid")) {
                if (targetPage) {
                    logger.error("Login succeeded with invalid credentials");
                    macc.clickLogout();
                    Assert.fail("Invalid login passed - redirected to My Account page");
                } else {
                    logger.info("Login failed as expected for invalid credentials");
                    Assert.assertTrue(true, "Invalid login correctly failed");
                }
            }

        } catch (Exception e) {
            logger.error("Test failed due to exception: {}", e.getMessage(), e);
            Assert.fail("Test case failed due to exception: " + e.getMessage());
        }

        logger.info("**** Finished TC003_LoginDDT ****");
    }
}