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
        driver.findElement(By.cssSelector("a.delete-note-button[data-note-title='" + title + "']"));

    wait.until(driver -> deleteButton.isDisplayed());
    deleteButton.click();
  }
}
