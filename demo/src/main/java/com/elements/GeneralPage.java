package com.elements;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.locators.RelativeLocator.*;
import com.common.Constant;

public class GeneralPage {
    // Locators -> will move to homepage later
    private final By listTabs = By.xpath("//div[@id='content']//ul//li//a");

    // Elements
    protected List<WebElement> getTabs() {
        return Constant.WEBDRIVER.findElements(listTabs);
    }

    public void clickTabByName(String tabName) {
        getTabs().stream()
                .filter(tab -> tab.getText().trim().equals(tabName))
                .findFirst()
                .ifPresent(tab -> tab.click());
    }

    public DataTablePage goToSortableDataTables() {
        this.clickTabByName("Sortable Data Tables");
        return new DataTablePage();
    }

    public void waitForElementVisible(By locator) {
        // Implement explicit wait logic here if needed
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementDisappear(WebElement locator) {
        WebDriverWait wait = new WebDriverWait(Constant.WEBDRIVER, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOf(locator));
    }
}
