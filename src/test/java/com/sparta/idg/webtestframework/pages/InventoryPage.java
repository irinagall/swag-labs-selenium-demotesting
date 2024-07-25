package com.sparta.idg.webtestframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InventoryPage {
    private WebDriver webDriver;
    private By inventoryItemLocator = By.className("inventory_item");
    private WebDriverWait wait;

    private By basketCountLocator = By.className("shopping_cart_badge");
   // private By addToCartButtonLocator = By.xpath("//button[starts-with(@id, 'add-to-cart')]");
    private By addToCartButtonLocator = By.cssSelector("button[id^='add-to-cart']");
    //private By removeFromCartButtonLocator = By.xpath("//button[starts-with(@id, 'remove-')]");
    private By removeFromCartButtonLocator = By.cssSelector("button[id^='remove-']");


    public InventoryPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }
    public List<WebElement> getInventoryItems(){
        return webDriver.findElements(inventoryItemLocator);
    }

    public int getInventoryItemCount(){
        return getInventoryItems().size();
    }

    public void waitForPageLoad(){
        wait.until(ExpectedConditions.presenceOfElementLocated(inventoryItemLocator));
    }

    public void addItemToBasket(int index){
        List<WebElement> addToCartButtons = webDriver.findElements(addToCartButtonLocator);
        if(index>= 0 && index < addToCartButtons.size()){
            addToCartButtons.get(index).click();
        } else {
            throw new IllegalArgumentException("Invalid item index");
        }
    }

    public int getBasketCount() {
        try {
            return Integer.parseInt(webDriver.findElement(basketCountLocator).getText());
        } catch (NoSuchElementException exception) {
            return 0;
        }
    }

    public void removeItemFromBasket(int index) {
        List<WebElement> removeFromCartButtons = webDriver.findElements(removeFromCartButtonLocator);
        if (index>= 0 && index < removeFromCartButtons.size()) {
            removeFromCartButtons.get(index).click();
        } else {
            throw new IllegalArgumentException("Invalid item index:" +  index);
        }
    }
}
