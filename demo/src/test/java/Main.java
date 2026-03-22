import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.common.Constant;
import com.elements.DataTablePage;
import com.elements.HomePage;

import components.BaseTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Main extends BaseTest {
    SoftAssert softAssert;
    DataTablePage dataTablePage;
    HomePage homePage;

    @BeforeMethod()
    public void beforeMethod() {
        System.out.println("Pre-condition");
        homePage = new HomePage();
        homePage.open();
        softAssert = new SoftAssert();
    }

    @Test(description = "TC01", retryAnalyzer = components.Retry.class)
    public void TC01() {
        dataTablePage = homePage.goToSortableDataTables();
        dataTablePage.clickHeaderByIndex(1);
        softAssert.assertTrue(dataTablePage.isColumnSorted(true),
                "Column is not sorted in ascending order");
        softAssert.assertTrue(dataTablePage.isColumnSorted(false),
                "Column is not sorted in ascending order");
    }

    @Test(description = "demo")
    public void demo() throws IOException {
        List<HashMap<String, String>> demoData = getDataFromJson(Constant.properties.getProperty("demodatalocation"));
        System.out.println(demoData);
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        Constant.WEBDRIVER.quit();
        softAssert.assertAll();
    }
}