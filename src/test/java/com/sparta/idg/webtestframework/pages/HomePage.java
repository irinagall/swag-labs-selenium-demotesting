package com.sparta.idg.webtestframework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver webDriver;

    private By userNameField = new By.ByName("user-name");
    private By passwordField = new By.ByName("password");
    private By loginButton = new By.ById("login-button");
    private By errorMessage = new By.ByCssSelector("#login_button_container .error-message-container.error h3");

    public HomePage(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    public void enterUsername( String username){
        webDriver.findElement(this.userNameField).sendKeys(username);
    }

    public void enterPassword(String password){
        webDriver.findElement(this.passwordField).sendKeys(password);
    }

    public void clickLoginButton(){
        webDriver.findElement(this.loginButton).click();
    }

    public String getErrorMessage(){
        return webDriver.findElement(this.errorMessage).getText();
    }

    public void loginWithValidCredentials(String validUsername, String validPassword) {
        enterUsername(validUsername);
        enterPassword(validPassword);
        clickLoginButton();
        waitForLoginToComplete();
    }

    private void waitForLoginToComplete() {
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }
}
