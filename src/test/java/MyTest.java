import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

import org.testng.Assert;

public class MyTest {
    private WebDriver driver;
    private String userName = "John Doe";
    private String userEmail = "johnnewerr.doe@example.com";
    private String userPassword = "Password123!";

    private String inviteLink;


    @BeforeClass
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testSignUpFlow() throws InterruptedException {
        // Navigate to your Next.js 14 app's homepage
        driver.get("http://localhost:3000/auth/admin");


        // Wait for the registration form to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        // Fill out the registration form with the provided data
        nameInput.sendKeys(userName);
        WebElement emailInput = driver.findElement(By.id("Email"));
        emailInput.sendKeys(userEmail);
        WebElement passwordInput = driver.findElement(By.id("Password"));
        passwordInput.sendKeys(userPassword);

        // Submit the form
        WebElement createButton = driver.findElement(By.id("Create"));
        createButton.click();

        Thread.sleep(1000);

        // Add assertions for successful registration or error handling if needed
        // ...
    }


    @Test(dependsOnMethods = "testSignUpFlow")
    public void testLoginFromNavbar() {
        // Navigate to your Next.js 14 app's homepage
        driver.get("http://localhost:3000");

        // Click on the "Login" button in the navbar
        WebElement loginButton = driver.findElement(By.id("Login"));
        loginButton.click();

        // Wait for the login page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        // Fill out the login form with the registered credentials
        emailInput.sendKeys(userEmail);
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys(userPassword);

        // Submit the login form
        WebElement loginSubmitButton = driver.findElement(By.id("In"));
        loginSubmitButton.click();

        // Add assertions for successful login or error handling if needed
        // ...
    }


    @Test(dependsOnMethods = "testLoginFromNavbar")
    public void testClickUserSetupLink() throws InterruptedException {
        WebDriverWait waitForSettingsPage = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitForSettingsPage.until(ExpectedConditions.urlContains("/settings"));

        Thread.sleep(1000);

        WebElement userSetupLink = driver.findElement(By.id("setup"));
        userSetupLink.click();

        // Wait for the modal to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("server-name")));

        // Fill in the server details
        WebElement serverNameInput = driver.findElement(By.id("server-name"));
        serverNameInput.sendKeys("Test Automation Stream");
        WebElement domainInput = driver.findElement(By.id("domain"));
        domainInput.sendKeys("example.com");

        // Submit the form
        WebElement createButton = driver.findElement(By.id("create"));
        createButton.click();

        // Wait for the server creation process to complete
        WebDriverWait waitForServerCreation = new WebDriverWait(driver, Duration.ofSeconds(30));
        waitForServerCreation.until(ExpectedConditions.urlContains("/servers"));
    }

    @Test(dependsOnMethods = "testClickUserSetupLink")
    public void testCreateVideoChannel() throws InterruptedException {
        // Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click on the "Create New Channel" button
        WebElement createNewChannelButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("createNewChannelButton")));
        createNewChannelButton.click();

        // Wait for the modal to appear
        WebElement modalDialog = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bg-white")));

        // Enter the channel name
        WebElement channelNameInput = modalDialog.findElement(By.id("channelname"));
        channelNameInput.sendKeys("Test Automation Stream");

        // Select the "VIDEO" channel type
        WebElement videoCheckbox = modalDialog.findElement(By.id("type-VIDEO"));
        videoCheckbox.click();

        // Click the "Create" button
        WebElement createButton = modalDialog.findElement(By.id("createnewchannel"));
        createButton.click();

        // Wait for the modal to close
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("bg-white")));


    }


    @Test(dependsOnMethods = {"testCreateVideoChannel"})
    public void testInviteNewMembers() throws InterruptedException {
        // Wait for 15 seconds for components to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Click on the "Invite New Members" button in the server sidebar header
        System.out.println("Clicking on the 'Invite New Members' button...");
        WebElement inviteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("inviteNewMembers")));
        inviteButton.click();
        System.out.println("Clicked on the 'Invite New Members' button.");

        // Wait for the invite dialog to appear
// Wait for the invite dialog to appear
        System.out.println("Waiting for the invite dialog to appear...");
        WebDriverWait waitForDialog = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement inviteDialog = waitForDialog.until(ExpectedConditions.visibilityOfElementLocated(By.className("bg-white")));
        Assert.assertNotNull(inviteDialog, "Invite dialog is not null");
        System.out.println("Invite dialog appeared successfully.");

// Generate a new link
        System.out.println("Generating a new link...");
        WebElement generateNewLinkButton = waitForDialog.until(ExpectedConditions.elementToBeClickable(By.id("generatenewlink")));
        generateNewLinkButton.click();
        System.out.println("Waiting for the link to be generated...");
        Thread.sleep(1500);
        System.out.println("New link generated.");

// Find the invite link input element
        WebDriverWait waitForLinkInput = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement inviteLinkInput = waitForLinkInput.until(ExpectedConditions.visibilityOfElementLocated(By.id("linkinput")));

// Get the value of the invite link
        String inviteLink = inviteLinkInput.getAttribute("value");

// Verify that the invite link is not null or empty
        if (inviteLink == null || inviteLink.isEmpty()) {
            System.out.println("Invite link is null or empty. Unable to proceed with the test case.");
            // You can choose to fail the test case or handle the situation according to your requirements
            Assert.fail("Invite link is null or empty.");
        } else {
            System.out.println("Invite Link: " + inviteLink);
            // Proceed with the test case using the inviteLink value
        }

// Print the invite link value
        System.out.println("Invite Link: " + inviteLink);


        // Copy the link
        System.out.println("Copying the link...");
        WebElement copyButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("copy-button")));
        copyButton.click();
        System.out.println("Link copied.");

        // Verify if link is copied successfully

        System.out.println("Link copied successfully.");

        // Close the dialog
        System.out.println("Closing the dialog...");
        WebElement closeButton = inviteDialog.findElement(By.id("close"));
        closeButton.click();
        System.out.println("Dialog closed.");

        // Wait for the dialog to close
        System.out.println("Waiting for the dialog to close...");
        wait.until(ExpectedConditions.invisibilityOf(inviteDialog));
        System.out.println("Dialog closed successfully.");
    }

    @Test(dependsOnMethods = {"testInviteNewMembers"})
    public void testLogout() {
        // Click on the "Logout" button
        System.out.println("Logging out the user...");
        WebElement logoutButton = driver.findElement(By.id("out"));
        logoutButton.click();
        System.out.println("User logged out successfully.");
    }

    @Test(dependsOnMethods = "testLogout")
    public void testJoinServerAsUser() throws InterruptedException, AWTException {

        WebElement signUpButton = driver.findElement(By.id("SignUp"));
        signUpButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));


        nameInput.sendKeys("New User");
        WebElement emailInput = driver.findElement(By.id("Email"));
        emailInput.sendKeys("newuser@example.com");
        WebElement passwordInput = driver.findElement(By.id("Password"));
        passwordInput.sendKeys("newuserPassword@124");

        // Submit the form
        WebElement createButton = driver.findElement(By.id("Create"));
        createButton.click();

        Thread.sleep(1000);
        // Wait for sign-up to complete

        WebElement loginButton = driver.findElement(By.id("Login"));
        loginButton.click();
        WebDriverWait waitLogin = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitLogin.until(ExpectedConditions.urlContains("/auth/login"));

        // Navigate to the login page
        driver.get("http://localhost:3000/auth/login");

        // Fill out the login form with the new user's credentials
        emailInput = driver.findElement(By.id("email"));
        emailInput.sendKeys("newuser@example.com");
        passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("newuserPassword@124");

        // Submit the login form
        WebElement loginSubmitButton = driver.findElement(By.id("In"));
        loginSubmitButton.click();

        // Wait for login to complete
        wait.until(ExpectedConditions.urlContains("/settings"));

        // Click on the "User Setup" link
        WebElement userSetupLink = driver.findElement(By.id("setup"));
        userSetupLink.click();

        // Wait for the "Join Server" modal to appear
        WebElement joinServerModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bg-white")));

        // Enter the invite link
        // Enter the invite link
        WebElement inviteLinkInput = joinServerModal.findElement(By.id("inviteLink"));
        inviteLinkInput.click(); // Click on the input field to focus on it

        // Create a Robot instance to simulate keyboard input
        Robot robot = new Robot();

        // Paste the clipboard contents into the input field
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Click the "Join" button
        WebElement joinButton = joinServerModal.findElement(By.id("join"));
        joinButton.click();

        // Wait for redirection to /servers/[serverId]
        wait.until(ExpectedConditions.urlContains("/servers"));

        // Assert that the redirection was successful
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/servers"), "Redirection to /servers/[serverId] was not successful");
    }

    @Test(dependsOnMethods = "testCreateVideoChannel")
    public void testJoinVideoChannel() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Find the newly created video channel
        String newChannelName = "Test Automation Stream";
        WebElement newVideoChannel = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/main[1]/div[1]/div[3]/aside[1]/div[1]/div[2]/div[1]/div[1]/div[4]/div[2]/button[1]/p[1]"));

        // Verify that the new video channel was found
        Assert.assertNotNull(newVideoChannel, "Could not find the new video channel");
        String channelName = newVideoChannel.getText();
        Assert.assertEquals(channelName, newChannelName, "Channel name does not match");

        // Click on the new video channel
        newVideoChannel.click();

        // Wait for the video call page to load
        wait.until(ExpectedConditions.urlContains("/channels"));

        // Wait for 30 seconds (simulating the video call)
        Thread.sleep(30000);


        // Add any additional assertions or actions related to the video call functionality
        // ...
    }

    @Test(dependsOnMethods = "testJoinVideoChannel")
    public void testConferenceRoomActions() throws InterruptedException {
        // Wait for the conference room to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement conferenceRoom = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("lk-video-conference")));

        // Switch to the chat
        WebElement chatToggle = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/main[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/button[2]"));
        chatToggle.click();

        // Wait for the chat to be visible
        WebElement chatInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html[1]/body[1]/div[1]/main[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/form[1]/input[1]")));

        // Enter the message "Start of The Test Automation Stream"
        chatInput.sendKeys("Start of The Test Automation Stream");

        // Submit the message
        WebElement sendButton = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/main[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[2]/form[1]/button[1]"));
        sendButton.click();

        // Wait for 15 seconds
        Thread.sleep(15000);


        // Add assertions to verify the message was sent and received
        // ...
    }


    @DataProvider(name = "invalidPasswords")
    public static Object[][] getInvalidPasswords() {
        return new Object[][] {
                {"password"},
                {"UPPERCASE"},
                {"lowercase"},
                {"NoNumbers"},
                {"NoSpecials123"}
        };
    }

    @Test(dataProvider = "invalidPasswords")
    public void testInvalidPasswords(String invalidPassword) {

        driver.get("http://localhost:3000/auth/admin");

        WebElement signUpButton = driver.findElement(By.id("SignUp"));
        signUpButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        nameInput.sendKeys("John Doe");
        WebElement emailInput = driver.findElement(By.id("Email"));
        emailInput.sendKeys("johnnew.doe@example.com");
        WebElement passwordInput = driver.findElement(By.id("Password"));
        passwordInput.sendKeys(invalidPassword);


        WebElement createButton = driver.findElement(By.id("Create"));
        createButton.click();

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}