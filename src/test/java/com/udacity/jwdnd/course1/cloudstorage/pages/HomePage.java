package com.udacity.jwdnd.course1.cloudstorage.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

  private final WebDriver driver;
  private String baseUrl;
  private WebDriverWait wait;

  @FindBy(id = "contentDiv")
  private WebElement homeContainer;

  @FindBy(css = "#logoutDiv button")
  private WebElement logoutButton;

  @FindBy(id = "nav-notes-tab")
  private WebElement notesTab;

  @FindBy(id = "add-a-new-note")
  private WebElement newNoteButton;

  @FindBy(id = "note-title")
  private WebElement noteTitle;

  @FindBy(id = "note-description")
  private WebElement noteDescription;

  @FindBy(css = "#noteModal button.btn-primary")
  private WebElement noteSubmit;

  @FindBy(id = "nav-credentials-tab")
  private WebElement credentialsTab;

  @FindBy(id = "add-a-new-credential")
  private WebElement newCredentialButton;

  @FindBy(id = "credential-url")
  private WebElement credentialUrl;

  @FindBy(id = "credential-username")
  private WebElement credentialUsername;

  @FindBy(id = "credential-password")
  private WebElement credentialPassword;

  @FindBy(css = "#credentialModal button.btn-primary")
  private WebElement credentialSubmit;

  public HomePage(WebDriver driver, String baseUrl) {
    PageFactory.initElements(driver, this);
    this.driver = driver;
    this.baseUrl = baseUrl;
    this.wait = new WebDriverWait(driver, 5);
  }

  public void getPage() {
    this.driver.get(this.baseUrl + "/home");
  }

  public void clickLogout() {
    logoutButton.click();
  }

  public void clickNotesTab() {
    notesTab.click();
  }

  public void clickCredentialsTab() {
    credentialsTab.click();
  }

  public void clickNewNoteButton() {
    wait.until(driver -> newNoteButton.isDisplayed());
    newNoteButton.click();
  }

  public void setNoteTitle(String text) {
    wait.until(driver -> noteTitle.isDisplayed());
    noteTitle.clear();
    noteTitle.sendKeys(text);
  }

  public void setNoteDescription(String text) {
    wait.until(driver -> noteDescription.isDisplayed());
    noteDescription.clear();
    noteDescription.sendKeys(text);
  }

  public void clickNoteSubmit() {
    noteSubmit.click();
  }

  public void clickEditButtonForNoteWithTitle(String title) {
    WebElement editButton =
        driver.findElement(
            By.cssSelector("button.edit-note-button[data-note-title='" + title + "']"));
    wait.until(driver -> editButton.isDisplayed());
    editButton.click();
  }

  public void clickDeleteButtonForNoteWithTitle(String title) {
    WebElement deleteButton =
        driver.findElement(By.cssSelector(".delete-note-button[data-note-title='" + title + "']"));

    wait.until(driver -> deleteButton.isDisplayed());
    deleteButton.click();
  }

  public void clickNewCredentialButton() {
    wait.until(driver -> newCredentialButton.isDisplayed());
    newCredentialButton.click();
  }

  public void setCredentialUrl(String text) {
    wait.until(driver -> credentialUrl.isDisplayed());
    credentialUrl.clear();
    credentialUrl.sendKeys(text);
  }

  public void setCredentialUsername(String text) {
    wait.until(driver -> credentialUsername.isDisplayed());
    credentialUsername.clear();
    credentialUsername.sendKeys(text);
  }

  public void setCredentialPassword(String text) {
    wait.until(driver -> credentialPassword.isDisplayed());
    credentialPassword.clear();
    credentialPassword.sendKeys(text);
  }

  public void clickCredentialSubmit() {
    credentialSubmit.click();
  }

  public void clickEditButtonForCredentialWithUrl(String url) {
    WebElement editButton =
        driver.findElement(
            By.cssSelector("button.edit-credential-button[data-credential-url='" + url + "']"));
    wait.until(driver -> editButton.isDisplayed());
    editButton.click();
  }

  public void clickDeleteButtonForCredentialWithUrl(String url) {
    WebElement deleteButton =
        driver.findElement(
            By.cssSelector("a.delete-credential-button[data-credential-url='" + url + "']"));
    wait.until(driver -> deleteButton.isDisplayed());
    deleteButton.click();
  }
}
