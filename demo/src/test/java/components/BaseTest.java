package components;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    WebDriver driver;
    Properties properties;

    public BaseTest() {
        try {
            FileInputStream fileInputStream = new FileInputStream(
                    System.getProperty("user.dir") + "\\demo\\src\\main\\resources\\GlobalData.properties");
            properties = new Properties();
            properties.load(fileInputStream);
            driver = initDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebDriver initDriver() {
        String browser = properties.getProperty("browser");
        switch (browser.toLowerCase()) {
            case "chrome":
                // Khởi tạo ChromeDriver
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                // Khởi tạo FirefoxDriver
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                // Khởi tạo EdgeDriver
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }

    @BeforeMethod(alwaysRun = true)
    public void lanunchApplication() {
        driver.navigate().to(properties.getProperty("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void closeApplication() {
        if (driver != null) {
            driver.close();
        }
    }
}
