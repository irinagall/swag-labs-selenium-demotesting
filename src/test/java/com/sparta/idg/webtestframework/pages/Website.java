package com.sparta.idg.webtestframework.pages;

import org.openqa.selenium.WebDriver;


public class Website {
    private HomePage  homePage;
    private WebDriver webDriver;
    private InventoryPage inventoryPage;

    public Website(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.homePage = new HomePage(webDriver);
        this.inventoryPage = new InventoryPage(webDriver);
    }

    public HomePage getHomePage() {
        return homePage;
    }
    public String getCurrentUrl(){
        return webDriver.getCurrentUrl();
    }

    public String getPageTitle(){
        return webDriver.getTitle();
    }

    public InventoryPage getInventoryPage(){
        return inventoryPage;
    }
}
