
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

public class Main {
    HomePage homePage;
    SoftAssert softAssert;
    DataTablePage dataTablePage;

    @BeforeMethod()
    public void beforeMethod() {
        System.out.println("Pre-condition");
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        Constant.WEBDRIVER = new ChromeDriver(options);
        Constant.WEBDRIVER.manage().window().maximize();
        homePage = new HomePage();
        homePage.open();
        softAssert = new SoftAssert();
    }

    @Test(description = "TC01")
    public void TC01() {
        dataTablePage = homePage.goToSortableDataTables();
        dataTablePage.clickHeaderByIndex(1);
        softAssert.assertTrue(dataTablePage.isColumnSorted(true),
                "Column is not sorted in ascending order");
        softAssert.assertTrue(dataTablePage.isColumnSorted(false),
                "Column is not sorted in ascending order");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        Constant.WEBDRIVER.quit();
        softAssert.assertAll();
    }
}