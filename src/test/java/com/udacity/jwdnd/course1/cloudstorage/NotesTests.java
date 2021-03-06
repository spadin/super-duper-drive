package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.helpers.UserServiceHelper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
      "spring.jpa.hibernate.ddl-auto=",
      "spring.datasource.url=",
      "spring.datasource.driver-class-name="
    })
class NotesTests {

  @LocalServerPort private Integer port;

  private Logger logger;
  private WebDriver driver;
  private String baseUrl;
  private LoginPage loginPage;
  private HomePage homePage;
  private UserServiceHelper userServiceHelper;

  @Autowired
  public NotesTests(UserServiceHelper userServiceHelper) {
    this.userServiceHelper = userServiceHelper;
  }

  @BeforeAll
  static void beforeAll() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  public void beforeEach() {
    this.logger = LoggerFactory.getLogger(NotesTests.class);

    this.driver = new ChromeDriver();
    this.baseUrl = "http://localhost:" + this.port;
    this.loginPage = new LoginPage(driver, baseUrl);
    this.homePage = new HomePage(driver, baseUrl);
  }

  @AfterEach
  public void afterEach() {
    if (this.driver != null) {
      driver.quit();
    }
  }

  protected void loginWithTestUser() {
    User user = this.userServiceHelper.createTestUser();

    loginPage.getPage();
    loginPage.login(user.getUsername(), user.getPassword());
  }

  protected void createNote(String title, String description) {

    homePage.getPage();
    homePage.clickNotesTab();
    homePage.clickNewNoteButton();

    homePage.setNoteTitle(title);
    homePage.setNoteDescription(description);
    homePage.clickNoteSubmit();
  }

  @Test
  @DisplayName("Create a new note")
  public void createANewNote() throws InterruptedException {
    loginWithTestUser();
    createNote("Test Note Title", "This is a test note");

    homePage.getPage();
    homePage.clickNotesTab();

    driver.findElement(By.xpath("//*[text()='Test Note Title']"));
    driver.findElement(By.xpath("//*[text()='This is a test note']"));
  }

  @Test
  @DisplayName("Update a note")
  public void updateANote() throws InterruptedException {
    loginWithTestUser();
    createNote("Test Note Title", "This is a test note");

    homePage.getPage();
    homePage.clickNotesTab();

    homePage.clickEditButtonForNoteWithTitle("Test Note Title");
    homePage.setNoteTitle("Updated note title");
    homePage.setNoteDescription("Updated note description");
    homePage.clickNoteSubmit();

    homePage.getPage();
    homePage.clickNotesTab();

    driver.findElement(By.xpath("//*[text()='Updated note title']"));
    driver.findElement(By.xpath("//*[text()='Updated note description']"));
  }

  @Test
  @DisplayName("Deletes a new note")
  public void deleteANote() throws InterruptedException {
    loginWithTestUser();

    // Confirm no notes are created yet.
    homePage.getPage();
    homePage.clickNotesTab();

    driver.findElement(By.xpath("//*[text()='No notes have been created yet.']"));

    createNote("Test Note Title", "This is a test note");

    homePage.getPage();
    homePage.clickNotesTab();

    homePage.clickDeleteButtonForNoteWithTitle("Test Note Title");
    homePage.getPage();
    homePage.clickNotesTab();

    // Confirm note is deleted by checking presence of no notes created message.
    driver.findElement(By.xpath("//*[text()='No notes have been created yet.']"));
  }
}
