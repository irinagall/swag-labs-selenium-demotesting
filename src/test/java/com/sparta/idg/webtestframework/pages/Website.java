package com.sparta.idg.webtestframework.pages;

import org.openqa.selenium.WebDriver;


public class Website {
    private HomePage  homePage;
    private WebDriver webDriver;
    private InventoryPage inventoryPage;
    private ProductPage productPage;

    public Website(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.homePage = new HomePage(webDriver);
        this.inventoryPage = new InventoryPage(webDriver);
        this.productPage = new ProductPage(webDriver);
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

    public ProductPage getProductPage() {
        return productPage;
    }
}
