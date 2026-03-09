package com.elements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.locators.RelativeLocator.*;
import com.common.Constant;

public class GeneralPage {
    // Locators
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

}
