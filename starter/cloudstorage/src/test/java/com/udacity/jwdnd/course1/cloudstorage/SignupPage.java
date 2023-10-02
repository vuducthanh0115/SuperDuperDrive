package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputUsername")
    private WebElement userName;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "inputFirstname")
    private WebElement firstName;

    @FindBy(id = "inputLastname")
    private WebElement lastName;

    @FindBy(id = "buttonSignUp")
    private WebElement buttonSignup;

    private WebDriver webDriver;

    public SignupPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void signup(String firstName, String lastName, String userName, String password) {
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.buttonSignup.click();
    }
}
