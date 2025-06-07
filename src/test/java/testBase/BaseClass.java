package testBase;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	public WebDriver driver;
	public Logger logger;
	public Properties p;

	@BeforeClass(groups= {"Sanity","Regression", "Master"})
	@Parameters({"os", "browser"})
	public void setUp(String os, String br) throws IOException {

		// Load config.properties
		FileReader file = new FileReader("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);

		logger = LogManager.getLogger(this.getClass());

		if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// Set OS
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN10);
			}
			else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}
			else if(os.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			}
			else {
				System.out.println("Not matching os");
				return;
			}

			// Set browser
			switch(br.toLowerCase()) {
				case "chrome": capabilities.setBrowserName("chrome"); break;
				case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
				case "firefox": capabilities.setBrowserName("firefox"); break;
				case "internet explorer": capabilities.setBrowserName("internet explorer"); break;
				default: System.out.println("Not matching browser"); return;
			}

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

			// ✅ Add necessary setup steps for remote environment
			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().window().maximize();
			driver.get(p.getProperty("appUrl"));
		}

		if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
			switch(br.toLowerCase()) {
				case "chrome": driver = new ChromeDriver(); break;
				case "edge": driver = new EdgeDriver(); break;
				case "firefox": driver = new FirefoxDriver(); break;
				default: System.out.println("invalid browser"); return;
			}

			driver.manage().deleteAllCookies();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.manage().window().maximize();
			driver.get(p.getProperty("appUrl"));
		}
	}

	@AfterClass(groups= {"Sanity","Regression", "Master"})
	public void teardown() {
		driver.quit();
	}

	public String randomString() {
		RandomStringGenerator generator = new RandomStringGenerator.Builder()
			.withinRange('a', 'z')
			.build();
		return generator.generate(10);
	}

	public String randomAlphanumeric() {
		RandomStringGenerator generator = new RandomStringGenerator.Builder()
			.withinRange('0', 'z')
			.filteredBy(Character::isLetterOrDigit)
			.build();
		return generator.generate(10);
	}
}
