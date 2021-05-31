package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  private SignupPage signupPage;
  private HomePage homePage;

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
    this.signupPage = new SignupPage(driver, baseUrl);
    this.homePage = new HomePage(driver, baseUrl);
  }

  @AfterEach
  public void afterEach() {
    if (this.driver != null) {
      driver.quit();
    }
  }

  protected void signupAndLogin() {
    signupPage.getPage();

    String username = "john-smith-2";
    String password = "super-secret";

    signupPage.signup("John", "Smith", username, password);

    loginPage.getPage();
    loginPage.login(username, password);
  }

  @Test
  @DisplayName("Create a new note")
  public void createANewNote() throws InterruptedException {
    signupAndLogin();

    homePage.getPage();
    homePage.clickNotesTab();
    homePage.clickNewNoteButton();

    homePage.setNoteTitle("Test Note Title");
    homePage.setNoteDescription("This is a test note");
    homePage.clickNoteSubmit();

    homePage.getPage();
    homePage.clickNotesTab();

    Thread.sleep(5000);
  }
}
