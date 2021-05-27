package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginPage {

  private final WebDriver driver;
  private String baseUrl;
  private WebDriverWait wait;
  private Logger logger;

  @FindBy(id = "inputUsername")
  private WebElement usernameInput;

  @FindBy(id = "inputPassword")
  private WebElement passwordInput;

  @FindBy(id = "submit-button")
  private WebElement submitButton;

  @FindBy(id = "success-msg")
  private WebElement successMessage;

  @FindBy(id = "login-container")
  private WebElement loginContainer;

  public LoginPage(WebDriver driver, String baseUrl) {
    PageFactory.initElements(driver, this);
    this.driver = driver;
    this.baseUrl = baseUrl;
    this.wait = new WebDriverWait(driver, 5);
    this.logger = LoggerFactory.getLogger(LoginPage.class);
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
    wait.until(driver -> submitButton.isDisplayed());
    submitButton.click();
  }

  public void setUsernameInput(String text) {
    logger.info("current url: " + driver.getCurrentUrl());
    logger.info("usernameInput displayed: " + usernameInput.isDisplayed());
    usernameInput.click();
    usernameInput.clear();
    usernameInput.sendKeys(text);
  }

  public void setPasswordInput(String text) {
    logger.info("passwordInput displayed: " + passwordInput.isDisplayed());
    passwordInput.click();
    passwordInput.clear();
    passwordInput.sendKeys(text);
  }

  public boolean isSuccessMessageDisplayed() {
    return successMessage.isDisplayed();
  }

  public void waitUntilLoaded() {
    wait.until(driver -> driver.getCurrentUrl().contains("/login"));
  }
}
