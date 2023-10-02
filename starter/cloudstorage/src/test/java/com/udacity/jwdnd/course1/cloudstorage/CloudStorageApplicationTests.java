package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testSignUpFeature() {
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Thanh", "Vu", "udacity", "12345@Udacity");

        // Check if the signup is successful and redirect to the login page
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

        //Check title is correct
        Assertions.assertEquals("Sign In - SuperDuperDrive", driver.getTitle());
    }

    @Test
    public void testLoginFeature() {
        // 1. Sign up
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Thanh", "Vu", "udacity", "12345@Udacity");

        // 2. Login
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("udacity", "12345@Udacity");

        //Check title is correct
        Assertions.assertEquals("SuperDuperDrive", driver.getTitle());

        // Check if the login is successful and redirect to the login page
        Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
    }

    @Test
    public void testNotLoginFeature() {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Sign In - SuperDuperDrive", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Sign In - SuperDuperDrive", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up - SuperDuperDrive", driver.getTitle());
    }

    @Test
    public void testLogoutFeature() {
        // 1. Signup
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Thanh", "Vu", "udacity", "12345@Udacity");
        Assertions.assertEquals("Sign In - SuperDuperDrive", driver.getTitle());

        // 2. Login
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("udacity", "12345@Udacity");
        Assertions.assertEquals("SuperDuperDrive", driver.getTitle());

        // 3. Logout
        driver.get("http://localhost:" + this.port + "/home");
        HomePage homePage = new HomePage(driver);
        homePage.logout();
        Assertions.assertEquals("Sign In - SuperDuperDrive", driver.getTitle());
    }

    @Test
    public void testNoteFeature() throws InterruptedException {

        // 1. Signup
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Thanh", "Vu", "udacity", "12345@Udacity");

        // 2. Login
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("udacity", "12345@Udacity");

        // 3. Add new note
        HomePage homePage = new HomePage(driver);
        homePage.addNote("Note1", "Description note 1.");

        // 4. Check if exist note
        driver.get("http://localhost:" + this.port + "/home");
        homePage.clickNoteTab();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th"))));
        Assertions.assertEquals("Note1", driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th")).getText());
        Assertions.assertEquals("Description note 1.", driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/td[2]")).getText());

        // 5. Check note revision
        homePage.editNote("Edit note 1", "Description after edit");
        driver.get("http://localhost:" + this.port + "/home");
        homePage.clickNoteTab();
        wait.until(ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th"))));
        Assertions.assertEquals("Edit note 1", driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/th")).getText());
        Assertions.assertEquals("Description after edit", driver.findElement(By.xpath("//*[@id='userTable']/tbody/tr/td[2]")).getText());

        // 6. Check delete note
        driver.get("http://localhost:" + this.port + "/home");
        homePage.deleteNote();
        Assertions.assertEquals(0, driver.findElements(By.xpath("//*[@id='userTable']/tbody")).size());
    }

    @Test
    public void testCredentialFeature() throws InterruptedException {
        // 1. Signup
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup("Thanh", "Vu", "udacity", "12345@Udacity");

        // 2. Login
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("udacity", "12345@Udacity");

        // 3. Add credential
        HomePage homePage = new HomePage(driver);
        homePage.addCredential("https://learn.udacity.com", "udacity", "12345@Udacity");
        driver.get("http://localhost:" + this.port + "/home");
        homePage.clickCredentialTab();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th"))));
        Assertions.assertEquals("https://learn.udacity.com", driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th")).getText());
        Assertions.assertEquals("udacity", driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[2]")).getText());

        // 4. Check credential revision
        homePage.editCredential("https://outlook.office.com/", "outlook", "12345@Outlook");
        driver.get("http://localhost:" + this.port + "/home");
        homePage.clickCredentialTab();
        wait.until(ExpectedConditions.visibilityOf
                (driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th"))));
        Assertions.assertEquals("https://outlook.office.com/", driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/th")).getText());
        Assertions.assertEquals("outlook", driver.findElement(By.xpath("//*[@id='credentialTable']/tbody/tr/td[2]")).getText());

        // 5. Check delete credential
        driver.get("http://localhost:" + this.port + "/home");
        homePage.deleteCredential();
        Assertions.assertEquals(0, driver.findElements(By.xpath("//*[@id='userTable']/tbody")).size());
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(String firstName, String lastName, String userName, String password) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up - SuperDuperDrive"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstname")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstname"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastname")));
        WebElement inputLastName = driver.findElement(By.id("inputLastname"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);


        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign-up was successful.
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depending on the rest of your code.
		*/
//         Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
    }


    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("SuperDuperDrive"));

    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a successful sign-up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "testRedirection", "udacity", "12345@Udacity");

        // Check if we have been redirected to the login page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "testBadUrl", "udacity", "12345@Udacity");
        doLogIn("udacity", "12345@Udacity");
        Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
        // Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
    }


    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "testLargeUpload", "udacity", "12345@Udacity");
        doLogIn("udacity", "12345@Udacity");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

    }

}
