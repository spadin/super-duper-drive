package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.helpers.UserServiceHelper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
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
class CredentialsTests {

  @LocalServerPort private Integer port;
  private Logger logger;
  private WebDriver driver;
  private String baseUrl;
  private LoginPage loginPage;
  private SignupPage signupPage;
  private HomePage homePage;
  private UserServiceHelper userServiceHelper;

  @Autowired
  public CredentialsTests(UserServiceHelper userServiceHelper) {
    this.userServiceHelper = userServiceHelper;
  }

  @BeforeAll
  static void beforeAll() {
    WebDriverManager.chromedriver().setup();
  }

  @BeforeEach
  public void beforeEach() {
    this.logger = LoggerFactory.getLogger(CredentialsTests.class);

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

  protected void loginWithTestUser() {
    User user = this.userServiceHelper.createTestUser();

    loginPage.getPage();
    loginPage.login(user.getUsername(), user.getPassword());
  }

  protected void createCredential(String url, String username, String password) {

    homePage.getPage();
    homePage.clickCredentialsTab();
    homePage.clickNewCredentialButton();

    homePage.setCredentialUrl(url);
    homePage.setCredentialUsername(username);
    homePage.setCredentialPassword(password);
    homePage.clickCredentialSubmit();
  }

  @Test
  @DisplayName("Create a new credential")
  public void createANewCredential() throws InterruptedException {
    loginWithTestUser();
    createCredential("http://google.com", "admin", "password");

    homePage.getPage();
    homePage.clickCredentialsTab();

    driver.findElement(By.xpath("//*[text()='admin']"));
    driver.findElement(By.xpath("//*[text()='http://google.com']"));
  }

  @Test
  @DisplayName("Update a credential")
  public void updateACredential() throws InterruptedException {
    loginWithTestUser();
    createCredential("http://google.com", "admin", "password");

    homePage.getPage();
    homePage.clickCredentialsTab();

    homePage.clickEditButtonForCredentialWithUrl("http://google.com");
    homePage.setCredentialUrl("http://yahoo.com");
    homePage.setCredentialUsername("root");
    homePage.clickCredentialSubmit();

    homePage.getPage();
    homePage.clickCredentialsTab();

    driver.findElement(By.xpath("//*[text()='root']"));
    driver.findElement(By.xpath("//*[text()='http://yahoo.com']"));
  }

  @Test
  @DisplayName("Deletes a new credential")
  public void deleteACredential() throws InterruptedException {
    loginWithTestUser();

    // Confirm no credentials are created yet.
    homePage.getPage();
    homePage.clickCredentialsTab();

    driver.findElement(By.xpath("//*[text()='No credentials have been created yet.']"));

    createCredential("http://google.com", "admin", "password");

    homePage.getPage();
    homePage.clickCredentialsTab();

    homePage.clickDeleteButtonForCredentialWithUrl("http://google.com");
    homePage.getPage();
    homePage.clickCredentialsTab();

    // Confirm credential is deleted by checking presence of no credentials created message.
    driver.findElement(By.xpath("//*[text()='No credentials have been created yet.']"));
  }
}
