package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

  private final WebDriver driver;
  private String baseUrl;

  @FindBy(id = "contentDiv")
  private WebElement homeContainer;

  @FindBy(css = "#logoutDiv button")
  private WebElement logoutButton;

  public HomePage(WebDriver driver, String baseUrl) {
    PageFactory.initElements(driver, this);
    this.driver = driver;
    this.baseUrl = baseUrl;
  }

  public void getPage() {
    this.driver.get(this.baseUrl + "/home");
  }

  public void clickLogout() {
    logoutButton.click();
  }
}
