package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage extends BasePage{
	WebDriver driver;

    public RegisterPage(WebDriver driver) {
		super(driver);
	}

 

@FindBy(xpath="//input[@id='FirstName']") 
WebElement txtFirstName;
@FindBy(xpath="//input[@id='LastName']")
WebElement txtLastName;

@FindBy(xpath="//input[@id='Email']") 
WebElement txtEmail;
@FindBy(xpath="//input[@id='Company']") 
WebElement txtCompanyName;
@FindBy(xpath="//input[@id='Password']") 
WebElement txtPassword;
@FindBy(xpath="//input[@id='ConfirmPassword']") 
WebElement txtConfirmPassword;
@FindBy(xpath="//button[@id='register-button']") 
WebElement btnRegister;

    @FindBy(xpath = "//div[@class='result']")
    private WebElement msgConfirmation;
    
    

    public void enterFirstName(String firstName) {
        txtFirstName.sendKeys(firstName);

    }

    public void enterLastName(String lastName) {
        txtLastName.sendKeys(lastName);
    }

    public void enterEmail(String email) {
        txtEmail.sendKeys(email);
    }
public void enterCompanyName(String companyName) {
	txtCompanyName.sendKeys(companyName);
}
    public void enterPassword(String password) {
        txtPassword.sendKeys(password);
       
    }
    public void enterConfirmationPassword(String password) {
    	 txtConfirmPassword.sendKeys(password);
    }

    public void clickRegister() {
        btnRegister.click();
    }

    public String getConfirmationMsg() {
    	try {
    		return(msgConfirmation.getText());
    	}catch(Exception e) {
    		return (e.getMessage());
    	}
    }
}


