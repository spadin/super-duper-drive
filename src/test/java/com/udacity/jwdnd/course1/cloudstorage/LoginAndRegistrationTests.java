package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.pages.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.pages.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.pages.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
class LoginAndRegistrationTests {

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
    this.logger = LoggerFactory.getLogger(LoginAndRegistrationTests.class);

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

  @Test
  @DisplayName("Confirm login page title")
  public void confirmLoginPageTitle() {
    loginPage.getPage();
    Assertions.assertEquals("Login", driver.getTitle());
  }

  @Test
  @DisplayName("Registering a new user redirects to login page")
  public void registerNewUser() throws InterruptedException {
    signupPage.getPage();

    String username = "john-smith";
    String password = "super-secret";

    signupPage.signup("John", "Smith", username, password);

    loginPage.waitUntilLoaded();
    Assertions.assertTrue(driver.getCurrentUrl().contains("/login"), "assert url contains /login");
    Assertions.assertTrue(
        loginPage.isSuccessMessageDisplayed(), "assert success message is displayed");
  }

  @Test
  @DisplayName("Visiting home redirects to login page when not logged in")
  public void homeRedirectToLoginPageWhenNotLoggedIn() {
    homePage.getPage();

    Assertions.assertTrue(driver.getCurrentUrl().contains("/login"), "assert url contains /login");
  }

  @Test
  @DisplayName("Logging in grants access to home, logging out takes it away")
  public void loggingInGetsYouAccessToHome() throws InterruptedException {
    signupPage.getPage();

    String username = "john-smith-2";
    String password = "super-secret";

    signupPage.signup("John", "Smith", username, password);

    loginPage.getPage();
    loginPage.login(username, password);

    homePage.getPage();
    Assertions.assertTrue(driver.getCurrentUrl().contains("/home"), "assert url contains /home");

    homePage.clickLogout();
    homePage.getPage();
    Assertions.assertTrue(driver.getCurrentUrl().contains("/login"), "assert url contains /login");
  }
}
