package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    WebDriver driver;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(xpath="//a[normalize-space()='Register']")
    WebElement linkRegister;
    

@FindBy(xpath="//a[normalize-space()='Log in']") 
WebElement logIn;

    public void clickRegister() {
        linkRegister.click();
    }
    
    public void clickLogin() {
    	logIn.click();
    }
}
