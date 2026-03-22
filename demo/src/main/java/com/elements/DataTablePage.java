package com.elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v136.page.Page;
import org.openqa.selenium.support.PageFactory;

import com.common.Constant;

public class DataTablePage extends GeneralPage {
    // WebDriver driver;

    // DataTablePage(WebDriver driver) {
    // super(driver);
    // PageFactory.initElements(driver, this);
    // }

    private final By listHeaderColumns = By.xpath("//table[@id='table1']//th//span");
    private final String columnCells = "//table[@id='table1']//tbody//tr[%d]";

    protected List<WebElement> getHeaderColumns() {
        return Constant.WEBDRIVER.findElements(listHeaderColumns);
    }

    public void clickHeaderColumn(String columnName) {
        getHeaderColumns().stream()
                .filter(column -> column.getText().trim().equals(columnName))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public void clickHeaderByIndex(int index) {
        List<WebElement> headers = getHeaderColumns();
        if (index < headers.size()) {
            headers.get(index).click();
        }
    }

    public int getColumnIndex(String columnName) {

        List<WebElement> headers = Constant.WEBDRIVER.findElements(listHeaderColumns);
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().trim().equals(columnName)) {
                return i + 1; // XPath bắt đầu từ 1
            }
        }
        return -1;
    }

    public List<String> getColumnData(String columnName) {
        int columnIndex = getColumnIndex(columnName);
        String xpath = String.format(columnCells, columnIndex);
        return Constant.WEBDRIVER.findElements(By.xpath(xpath))
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public boolean isColumnSortedAscending(String columnName) {
        List<String> actualList = getColumnData(columnName);
        List<String> sortedList = new ArrayList<>(actualList);
        Collections.sort(sortedList);

        return actualList.equals(sortedList);
    }

    public boolean isColumnSortedDescending(String columnName) {
        List<String> actualList = getColumnData(columnName);
        List<String> sortedList = new ArrayList<>(actualList);
        sortedList.sort(Collections.reverseOrder());
        return actualList.equals(sortedList);
    }

    public boolean isColumnSorted(boolean ascending) {
        for (WebElement element : getHeaderColumns()) {
            String headerText = element.getText().trim();
            if (ascending) {
                element.click();
            } else {
                element.click();
                element.click();
            }
            if (ascending) {
                if (!isColumnSortedAscending(headerText))
                    return false;
            } else {
                if (!isColumnSortedDescending(headerText))
                    return false;
            }
        }
        return true;
    }
}
