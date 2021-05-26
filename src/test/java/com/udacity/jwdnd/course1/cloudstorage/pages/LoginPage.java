package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

  private final WebDriver driver;
  private String baseUrl;

  @FindBy(id = "inputUsername")
  private WebElement usernameInput;

  @FindBy(id = "inputPassword")
  private WebElement passwordInput;

  @FindBy(id = "submit-button")
  private WebElement submitButton;

  @FindBy(id = "success-msg")
  private WebElement successMessage;

  public LoginPage(WebDriver driver, String baseUrl) {
    PageFactory.initElements(driver, this);
    this.driver = driver;
    this.baseUrl = baseUrl;
  }

  public void getPage() {
    this.driver.get(this.baseUrl + "/login");
  }

  public void login(String username, String password) {
    setUsernameInput(username);
    setPasswordInput(password);
    clickSubmitButton();
  }

  public void clickSubmitButton() {
    submitButton.click();
  }

  public void setUsernameInput(String text) {
    usernameInput.sendKeys(text);
  }

  public void setPasswordInput(String text) {
    passwordInput.sendKeys(text);
  }

  public boolean isSuccessMessageDisplayed() {
    return successMessage.isDisplayed();
  }
}
