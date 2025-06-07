package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}


@FindBy(xpath="//input[@id='Email']") 
WebElement txt_email;
@FindBy(xpath="//input[@id='Password']") 
WebElement txt_password;
@FindBy(xpath="//button[normalize-space()='Log in']") 
WebElement bttn_logIn;
	
public void setEmail(String email) {
	txt_email.sendKeys(email);
}

public void setPassword(String pwd) {
	txt_password.sendKeys(pwd);
}

public void clickLogin() {
	bttn_logIn.click();
}

}
