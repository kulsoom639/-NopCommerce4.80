package testCases; 

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.RegisterPage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {


	
    @Test(groups= {"Regression", "Master"})
	public void verifyAccountRegistration()  {
		
		logger.info("Starting TC001_AccountRegistrationTest");
		
		try {
	    HomePage hp = new HomePage(driver);
	    hp.clickRegister();
logger.info("Clicked on Register link");

	    RegisterPage regPage = new RegisterPage(driver);
	  String password = randomAlphanumeric();
	  logger.info("Providing customer details");
	    regPage.enterFirstName(randomString().toUpperCase());
	    regPage.enterLastName(randomString().toUpperCase());
	    regPage.enterEmail(randomString() + "@gmail.com");
	    regPage.enterCompanyName(randomString().toUpperCase());
	    regPage.enterPassword(password);
	    regPage.enterConfirmationPassword(password);

	    regPage.clickRegister();
logger.info("Validating expected message");
	    String msg = regPage.getConfirmationMsg().trim();
	    
	    if(msg.equals("Your registration completed")) {
	    	Assert.assertTrue(true);
	    }
	    else {
	    	logger.error("Test failed");
			logger.debug("debugs logs");
	    	Assert.assertTrue(false);
	    }
	 //   Assert.assertEquals(msg, "Your registration completed");
	}
		catch(Exception e) {
		
			Assert.fail();
		}
logger.info("Finished test case");
}
}