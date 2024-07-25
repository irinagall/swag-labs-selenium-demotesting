package com.sparta.idg.webtestframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {
    private WebDriver webDriver;

    private By productNameLocator = By.className("inventory_details_name");

    public ProductPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String getProductName() {
        return webDriver.findElement(productNameLocator).getText();
    }
}
