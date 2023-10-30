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
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RubricTest {
    private String webUrl;

    private WebDriver webDriver;

    @LocalServerPort
    private int localPort;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        webDriver = new ChromeDriver();
        webUrl = "http://localhost:" + localPort;
    }

    @AfterEach
    public void afterEach() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    /**
    * Create a dummy account for logging in later.
    * Visit the sign-up page.
    * Fill out credentials
    * */
    private void doMockSignUp(){

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        webDriver.get("http://localhost:" + localPort + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement firstNameField = webDriver.findElement(By.id("inputFirstName"));
        firstNameField.click();
        firstNameField.sendKeys("ziad");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement lastNameField = webDriver.findElement(By.id("inputLastName"));
        lastNameField.click();
        lastNameField.sendKeys("ziad");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement userNameField = webDriver.findElement(By.id("inputUsername"));
        userNameField.click();
        userNameField.sendKeys("ZIAD");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement passwordField = webDriver.findElement(By.id("inputPassword"));
        passwordField.click();
        passwordField.sendKeys("ZiadPassword");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = webDriver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

        Assertions.assertEquals("http://localhost:" + localPort + "/login?success", webDriver.getCurrentUrl());
    }

    /**
     * Log in to our account.
     */
    private void doLogIn() {
        webDriver.get("http://localhost:" + localPort + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = webDriver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys("ZIAD");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = webDriver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys("ZiadPassword");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = webDriver.findElement(By.id("login-button"));
        loginButton.click();
        try {
            webDriverWait.until(ExpectedConditions.titleContains("Home"));
        }catch(Exception ignored){}
    }

    /**
     * Assert that we were successfully redirected to log in screen
     * Redirected to log in again.
     */
    private void LogOut(){
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
        WebElement logOutBtn = webDriver.findElement(By.id("logout"));
        logOutBtn.click();
        Assertions.assertEquals("http://localhost:" + localPort + "/login?logout", webDriver.getCurrentUrl());
        webDriver.get(webUrl + "/home");
        Assertions.assertEquals("http://localhost:" + localPort + "/login", webDriver.getCurrentUrl());
    }



    /**
     * Test flow
     *  1) sign up
     *  2) login
     *  3) logout
     * Rubric:
     * Write a Selenium test that signs up a new user, logs that user in, verifies that they can access the home page,
     * then logs out and verifies that the home page is no longer accessible.
     */
    @Test
    @Order(1)
    void testFlowTillLogout() {
        doMockSignUp();
        doLogIn();
        LogOut();
    }

    /**
     * Test that going home without logging in redirects back to log in.
     * Rubric:
     * Write a Selenium test that verifies that the home page is not accessible without logging in.
     */
    @Test
    void testHomeInaccessible(){
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Login"));

        Assertions.assertEquals(webUrl + "/login", webDriver.getCurrentUrl());
    }

    /**
     * Rubric:
     * Write a Selenium test that logs in an existing user,
     * creates a note and verifies that the note details are visible in the note list.
     */
    @Test
    @Order(2)
    void testNoteCreation(){
        doLogIn();

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement noteTabBtn = webDriver.findElement(By.id("nav-notes-tab"));
        noteTabBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNote")));
        WebElement addNoteBtn = webDriver.findElement(By.id("addNote"));
        addNoteBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitleTextArea = webDriver.findElement(By.id("note-title"));
        noteTitleTextArea.click();
        noteTitleTextArea.sendKeys("Title text");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescTextArea = webDriver.findElement(By.id("note-description"));
        noteDescTextArea.click();
        noteDescTextArea.sendKeys("Desc text");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveChanges")));
        WebElement saveChangesBtn = webDriver.findElement(By.id("saveChanges"));
        saveChangesBtn.click();

        Assertions.assertEquals(webUrl + "/result?success=1", webDriver.getCurrentUrl());

        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        noteTabBtn = webDriver.findElement(By.id("nav-notes-tab"));
        noteTabBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title-display")));
        WebElement noteTitleDisplay = webDriver.findElement(By.id("note-title-display"));
        WebElement noteDescDisplay = webDriver.findElement(By.id("note-description-display"));

        Assertions.assertEquals("Title text",noteTitleDisplay.getText());
        Assertions.assertEquals("Desc text", noteDescDisplay.getText());
    }

    /**
     * Rubric:
     * Write a Selenium test that logs in an existing user with existing notes,
     * clicks the edit note button on an existing note, changes the note data,
     * saves the changes, and verifies that the changes appear in the note list.
     */
    @Test
    @Order(3)
    void testChangeNote(){
        doLogIn();

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement noteTabBtn = webDriver.findElement(By.id("nav-notes-tab"));
        noteTabBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editNote")));
        WebElement noteEditBtn = webDriver.findElement(By.id("editNote"));
        noteEditBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitleTextArea = webDriver.findElement(By.id("note-title"));
        noteTitleTextArea.click();
        noteTitleTextArea.sendKeys("Title text");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
        WebElement noteDescTextArea = webDriver.findElement(By.id("note-description"));
        noteDescTextArea.click();
        noteDescTextArea.sendKeys("Desc text");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveChanges")));
        WebElement saveChangesBtn = webDriver.findElement(By.id("saveChanges"));
        saveChangesBtn.click();

        //test the note is visible
        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        noteTabBtn = webDriver.findElement(By.id("nav-notes-tab"));
        noteTabBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title-display")));
        WebElement noteTitleDisplay = webDriver.findElement(By.id("note-title-display"));
        WebElement noteDescDisplay = webDriver.findElement(By.id("note-description-display"));

        Assertions.assertEquals("Title textTitle text",noteTitleDisplay.getText());
        Assertions.assertEquals("Desc textDesc text", noteDescDisplay.getText());
    }

    /**
     * Rubric:
     * Write a Selenium test that logs in an existing user with existing notes,
     * clicks the delete note button on an existing note,
     * and verifies that the note no longer appears in the note list.
     */
    @Test
    @Order(4)
    void testNoteDelete(){
        doLogIn();

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        WebElement noteTabBtn = webDriver.findElement(By.id("nav-notes-tab"));
        noteTabBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteNote")));
        WebElement noteDeleteBtn = webDriver.findElement(By.id("deleteNote"));
        noteDeleteBtn.click();

        Assertions.assertEquals(webUrl + "/result?success=1", webDriver.getCurrentUrl());
        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        noteTabBtn = webDriver.findElement(By.id("nav-notes-tab"));
        noteTabBtn.click();

        Assertions.assertTrue(webDriver.findElements(By.id("note-title-display")).isEmpty());
    }

    /**
     * Rubric:
     * Write a Selenium test that logs in an existing user,
     * creates a credential and verifies that the credential details are visible
     * in the credential list.
     */
    @Test
    @Order(5)
    void testCredCreation(){
        doLogIn();

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credTabBtn = webDriver.findElement(By.id("nav-credentials-tab"));
        credTabBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addCred")));
        WebElement addCredBtn = webDriver.findElement(By.id("addCred"));
        addCredBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credUrlTextArea = webDriver.findElement(By.id("credential-url"));
        credUrlTextArea.click();
        credUrlTextArea.sendKeys("test.url");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement credUsernameTextArea = webDriver.findElement(By.id("credential-username"));
        credUsernameTextArea.click();
        credUsernameTextArea.sendKeys("test user");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement credPassTextArea = webDriver.findElement(By.id("credential-password"));
        credPassTextArea.click();
        credPassTextArea.sendKeys("test pass");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialSubmitBtn")));
        WebElement saveChangesBtn = webDriver.findElement(By.id("credentialSubmitBtn"));
        saveChangesBtn.click();

        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        credTabBtn = webDriver.findElement(By.id("nav-credentials-tab"));
        credTabBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url-display")));
        WebElement credUrlDisplay = webDriver.findElement(By.id("credential-url-display"));
        WebElement credUsernameDisplay = webDriver.findElement(By.id("credential-username-display"));
        WebElement credPassDisplay = webDriver.findElement(By.id("credential-password-display"));

        Assertions.assertEquals("test.url", credUrlDisplay.getText());
        Assertions.assertEquals("test user", credUsernameDisplay.getText());
        Assertions.assertEquals("test pass", credPassDisplay.getText());

    }

    /**
     * Rubric:
     * Write a Selenium test that logs in an existing user with existing credentials,
     * clicks the edit credential button on an existing credential, changes the credential data,
     * saves the changes, and verifies that the changes appear in the credential list.
     */
    @Test
    @Order(6)
    void testCredEdit(){
        doLogIn();

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credTabBtn = webDriver.findElement(By.id("nav-credentials-tab"));
        credTabBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editCred")));
        WebElement editCredBtn = webDriver.findElement(By.id("editCred"));
        editCredBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement credUrlTextArea = webDriver.findElement(By.id("credential-url"));
        credUrlTextArea.click();
        credUrlTextArea.sendKeys("test.url");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
        WebElement credUsernameTextArea = webDriver.findElement(By.id("credential-username"));
        credUsernameTextArea.click();
        credUsernameTextArea.sendKeys("test user");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        WebElement credPassTextArea = webDriver.findElement(By.id("credential-password"));
        credPassTextArea.click();
        credPassTextArea.sendKeys("test pass");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialSubmitBtn")));
        WebElement saveChangesBtn = webDriver.findElement(By.id("credentialSubmitBtn"));
        saveChangesBtn.click();

        Assertions.assertEquals(webUrl + "/result?success=1", webDriver.getCurrentUrl());
        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        credTabBtn = webDriver.findElement(By.id("nav-credentials-tab"));
        credTabBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url-display")));
        WebElement credUrlDisplay = webDriver.findElement(By.id("credential-url-display"));
        WebElement credUsernameDisplay = webDriver.findElement(By.id("credential-username-display"));
        WebElement credPassDisplay = webDriver.findElement(By.id("credential-password-display"));

        Assertions.assertEquals("test.urltest.url",credUrlDisplay.getText());
        Assertions.assertEquals("test usertest user", credUsernameDisplay.getText());
        Assertions.assertEquals("test passtest pass", credPassDisplay.getText());
    }

    @Test
    @Order(7)
    void testCredDelete(){
        doLogIn();

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Duration.ofSeconds(2));
        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        WebElement credTabBtn = webDriver.findElement(By.id("nav-credentials-tab"));
        credTabBtn.click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("deleteCred")));
        WebElement credDeleteBtn = webDriver.findElement(By.id("deleteCred"));
        credDeleteBtn.click();

        Assertions.assertEquals(webUrl + "/result?success=1", webDriver.getCurrentUrl());
        webDriver.get(webUrl + "/home");
        webDriverWait.until(ExpectedConditions.titleContains("Home"));

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        credTabBtn = webDriver.findElement(By.id("nav-credentials-tab"));
        credTabBtn.click();

        Assertions.assertTrue(webDriver.findElements(By.id("credential-url-display")).isEmpty());
    }
}
