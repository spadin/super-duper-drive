package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

  private final String baseUrl;
  private final WebDriver driver;

  @FindBy(id = "inputUsername")
  private WebElement usernameInput;

  @FindBy(id = "inputPassword")
  private WebElement passwordInput;

  @FindBy(id = "inputFirstName")
  private WebElement firstNameInput;

  @FindBy(id = "inputLastName")
  private WebElement lastNameInput;

  @FindBy(id = "submit-button")
  private WebElement submitButton;

  public SignupPage(WebDriver driver, String baseUrl) {
    PageFactory.initElements(driver, this);
    this.driver = driver;
    this.baseUrl = baseUrl;
  }

  public void getPage() {
    this.driver.get(this.baseUrl + "/signup");
  }

  public void signup(String firstName, String lastName, String username, String password) {
    setFirstNameInput(firstName);
    setLastNameInput(lastName);
    setUsernameInput(username);
    setPasswordInput(password);
    clickSubmitButton();
  }

  public void clickSubmitButton() {
    submitButton.click();
  }

  public void setFirstNameInput(String text) {
    firstNameInput.sendKeys(text);
  }

  public void setLastNameInput(String text) {
    lastNameInput.sendKeys(text);
  }

  public void setUsernameInput(String text) {
    usernameInput.sendKeys(text);
  }

  public void setPasswordInput(String text) {
    passwordInput.sendKeys(text);
  }
}
