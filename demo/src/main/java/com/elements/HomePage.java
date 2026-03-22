package com.elements;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.common.Constant;

import io.github.bonigarcia.wdm.WebDriverManager;

public class HomePage extends GeneralPage {

    public HomePage open() {
        initial();
        Constant.WEBDRIVER.get(Constant.SELE2_URL);
        return this;
    }

    public void initial() {
        try {
            FileInputStream fileInputStream = new FileInputStream(
                    System.getProperty("user.dir") + "\\src\\main\\java\\com\\resources\\GlobalData.properties");
            Constant.properties = new Properties();
            Constant.properties.load(fileInputStream);
            Constant.WEBDRIVER = initDriver();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebDriver initDriver() {
        String browser = System.getProperty("browser") != null ? System.getProperty("browser")
                : Constant.properties.getProperty("browser");
        // String browser = Constant.properties.getProperty("browser");
        WebDriver driver;
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

}