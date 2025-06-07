package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
	}

@FindBy(xpath="//h1[normalize-space()='My account - Customer info']")
WebElement msgHeading;

@FindBy(xpath="//a[normalize-space()='Log out']") 
WebElement logOut;

public boolean isMyAccountPageExists() {
    try {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement accountLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("My account")));
        return accountLink.isDisplayed();
    } catch (Exception e) {
        return false;
    }
}

	public void clickLogout() {
	logOut.click();	
	}
	
}

