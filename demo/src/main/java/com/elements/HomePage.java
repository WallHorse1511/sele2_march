package com.elements;

import java.time.Duration;

import com.common.Constant;

public class HomePage extends GeneralPage {
    public HomePage open() {

        Constant.WEBDRIVER.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Constant.WEBDRIVER.navigate().to(Constant.SELE2_URL);
        return this;
    }

}