package com.sparta.idg.webtestframework.tests;

import com.sparta.idg.webtestframework.pages.Website;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.*;

public class SwagLabTests_POM extends TestSetup {


    private static final String BASE_URL = "https://www.saucedemo.com/";


    private Website website;


    @Test
    @DisplayName("Given I enter a valid username and password, when I click login, then I should land on the inventory page")
    public void successfulLogin(){
        website = getWebsite(BASE_URL);
        website.getHomePage().enterUsername("standard_user");
        website.getHomePage().enterPassword("secret_sauce");
        website.getHomePage().clickLoginButton();
        MatcherAssert.assertThat(website.getCurrentUrl(), is(BASE_URL + "inventory.html"));
    }

    // Test the sad path (i.e. when the error message appears
    @Test
    @DisplayName("Given I enter an invalid username or password, when I click login, then I should see an error message")
    public void unsuccessfulLogin() {
        website = getWebsite(BASE_URL);
        website.getHomePage().enterUsername("invalid_user");
        website.getHomePage().enterPassword("invalid_password");
        website.getHomePage().clickLoginButton();

        String expectedErrorMessage = "Epic sadface: Username and password do not match any user in this service";

        Assertions.assertEquals(expectedErrorMessage, website.getHomePage().getErrorMessage());

    }


    // Test that when you login there are 6 items on the inventory page
    @Test
    @DisplayName("Given I successfully login, when I'm on the inventory page, then I should see 6 items")
    public void checkInventoryItemCount(){
        //Arrange
        website = getWebsite(BASE_URL);
        website.getHomePage().enterUsername("standard_user");
        website.getHomePage().enterPassword("secret_sauce");

        //Act
        website.getHomePage().clickLoginButton();

        //Wait for the inventory page to load
        website.getInventoryPage().waitForPageLoad();

        //Assert
        int itemCount = website.getInventoryPage().getInventoryItemCount();
        Assertions.assertEquals(6, itemCount);
    }

    @Test
    @DisplayName("Given I am on the inventory page, when I add an intem to the basket, then the basket count should increase by 1")
    public void addingItemIncreasesBasketCount(){
        //Arrange
        website = getWebsite(BASE_URL);
        website.getHomePage().enterUsername("standard_user");
        website.getHomePage().enterPassword("secret_sauce");
        website.getHomePage().clickLoginButton();

        website.getInventoryPage().waitForPageLoad();

        //Get initial basket count
        int initialBasketCount = website.getInventoryPage().getBasketCount();

        //Act
        website.getInventoryPage().addItemToBasket(0);

        //Assert
        int newBasketCount = website.getInventoryPage().getBasketCount();
        Assertions.assertEquals(initialBasketCount + 1, newBasketCount);

    }


}
