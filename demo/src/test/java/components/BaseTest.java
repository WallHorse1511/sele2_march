package components;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
    WebDriver driver;
    protected Properties properties;

    public void initial() {
        try {
            FileInputStream fileInputStream = new FileInputStream(
                    System.getProperty("user.dir") + "\\src\\main\\java\\com\\resources\\GlobalData.properties");
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
        initial();
        driver.navigate().to(properties.getProperty("url"));
    }

    @AfterMethod(alwaysRun = true)
    public void closeApplication() {
        if (driver != null) {
            driver.close();
        }
    }

    public List<HashMap<String, String>> getDataFromJson(String filePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new File(System.getProperty("user.dir") + "\\" + filePath),
                "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });

    }

    public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir") + "\\reports\\" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationFile));
        return destinationFile;
    }
}
