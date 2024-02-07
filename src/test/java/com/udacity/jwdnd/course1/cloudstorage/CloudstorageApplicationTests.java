package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {
	private static String firstName = "Victor";
	private static String lastName = "Menoscal";
	private static String userName = "root";
	private static String password = "password";
	private static String title = "Super Title";
	private static String description = "Super Duper Description";
	private static String credURL = "http://localhost:8080/home";
	private static String newTitle = "New Super Title";
	private static String newcredURL = "http://localhost:8080";

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
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getUnauthorizedHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void newUserSuperDuper() {
		doMockSignUp(firstName,lastName,userName,password);
		doLogInOut(userName, password);

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password) {
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys(firstName);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys(lastName);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
		webDriverWait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}



	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password) {
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.sendKeys(userName);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.sendKeys(password);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		//webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());
	}

	private void doLogInOut(String userName, String password) {
		// Log in.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.sendKeys(userName);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.sendKeys(password);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		//webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertEquals("Home", driver.getTitle());

		// Log out.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
		WebElement logoutButton = driver.findElement(By.id("logout"));
		logoutButton.click();

		//webDriverWait.until(ExpectedConditions.titleContains("Login"));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling redirecting users
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric:
	 * https://review.udacity.com/#!/rubrics/2724/view
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");

		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling bad URLs
	 * gracefully, for example with a custom error page.
	 *
	 * Read more about custom error pages at:
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");

		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
	 * rest of your code.
	 * This test is provided by Udacity to perform some basic sanity testing of
	 * your code to ensure that it meets certain rubric criteria.
	 *
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code.
	 *
	 * Read more about file size limits here:
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
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

	@Test
	public void noteAddSuperDuper() {
		String userName = "noteAdd";
		doMockSignUp(firstName,lastName,userName,password);
		doLogIn(userName, password);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		// added note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note")));
		WebElement newNote = driver.findElement(By.id("new-note"));
		newNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys(title);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.sendKeys(description);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note")));
		WebElement saveButton = driver.findElement(By.id("save-note"));
		saveButton.click();

		//webDriverWait.until(ExpectedConditions.titleContains("Result"));
		Assertions.assertEquals("Result", driver.getTitle());

		// check note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-link")));
		WebElement homeButton = driver.findElement(By.id("success-link"));
		homeButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTableBody")));
		WebElement notesTable = driver.findElement(By.id("userTableBody"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
		Boolean isAdd = false;
		for(int i=0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals(title)) {
				isAdd = true;
				break;
			}
		}
		Assertions.assertTrue(isAdd);
	}

	@Test
	public void noteEditSuperDuper() {
		String title = "Title Edit";
		String description = "Description Edit";
		String userName = "noteEdit";
		doMockSignUp(firstName,lastName,userName,password);
		doLogIn(userName, password);
		noteMock(title, description);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		// edit note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTableBody")));
		WebElement notesTable = driver.findElement(By.id("userTableBody"));
		List<WebElement> notesListDetails = notesTable.findElements(By.tagName("th"));
		WebElement parent = null;
		for(int i=0; i < notesListDetails.size(); i++) {
			WebElement element = notesListDetails.get(i);
			if (element.getAttribute("innerHTML").equals(title)) {
				parent = (WebElement) ((JavascriptExecutor) driver).executeScript(
						"return arguments[0].parentNode;", element);
			}
		}
		List<WebElement> notesList = parent.findElements(By.tagName("td"));
		WebElement editElement = null;
		for(int i=0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			editElement = element.findElement(By.name("edit"));
			if (editElement != null) {
				break;
			}
		}
		editElement.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.clear();
		noteTitle.sendKeys(newTitle);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note")));
		WebElement saveButton = driver.findElement(By.id("save-note"));
		saveButton.click();

		//webDriverWait.until(ExpectedConditions.titleContains("Result"));
		Assertions.assertEquals("Result", driver.getTitle());

		// check note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-link")));
		WebElement homeButton = driver.findElement(By.id("success-link"));
		homeButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTableBody")));
		notesTable = driver.findElement(By.id("userTableBody"));
		notesList = notesTable.findElements(By.tagName("th"));
		Boolean isEdit = false;
		for(int i=0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals(newTitle)) {
				isEdit = true;
				break;
			}
		}
		Assertions.assertTrue(isEdit);
	}

	@Test
	public void noteDeleteSuperDuper() {
		String title = "Title Delete";
		String description = "Description Delete";
		String userName = "noteDelete";
		doMockSignUp(firstName,lastName,userName,password);
		doLogIn(userName, password);
		noteMock(title, description);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		// delete note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTableBody")));
		WebElement notesTable = driver.findElement(By.id("userTableBody"));
		List<WebElement> notesListDetails = notesTable.findElements(By.tagName("td"));
		WebElement parent = null;
		for(int i=0; i < notesListDetails.size(); i++) {
			WebElement element = notesListDetails.get(i);
			if (element.getAttribute("innerHTML").equals(description)) {
				parent = (WebElement) ((JavascriptExecutor) driver).executeScript(
						"return arguments[0].parentNode;", element);
			}
		}
		List<WebElement> notesList = parent.findElements(By.tagName("td"));
		WebElement deleteElement = null;
		for(int i=0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			deleteElement = element.findElement(By.name("delete"));
			if (deleteElement != null) {
				break;
			}
		}
		deleteElement.click();

		//webDriverWait.until(ExpectedConditions.titleContains("Result"));
		Assertions.assertEquals("Result", driver.getTitle());

		// check note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-link")));
		WebElement homeButton = driver.findElement(By.id("success-link"));
		homeButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTableBody")));
		notesTable = driver.findElement(By.id("userTableBody"));
		notesList = notesTable.findElements(By.tagName("td"));
		Boolean isDelete = true;
		for(int i=0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals(description)) {
				isDelete = false;
				break;
			}
		}
		Assertions.assertTrue(isDelete);
	}

	@Test
	public void credentialAddSuperDuper() {
		String userName = "credentialAdd";
		doMockSignUp(firstName,lastName,userName,password);
		doLogIn(userName, password);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		// added credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credential")));
		WebElement newCredential = driver.findElement(By.id("new-credential"));
		newCredential.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys(credURL);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys(userName);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys(password);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credential")));
		WebElement saveButton = driver.findElement(By.id("save-credential"));
		saveButton.click();

		//webDriverWait.until(ExpectedConditions.titleContains("Result"));
		Assertions.assertEquals("Result", driver.getTitle());

		// check Credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-link")));
		WebElement homeButton = driver.findElement(By.id("success-link"));
		homeButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTableBody")));
		WebElement credentialsTable = driver.findElement(By.id("credentialTableBody"));
		List<WebElement> credentialsList = credentialsTable.findElements(By.tagName("th"));
		Boolean isAdd = false;
		for(int i=0; i < credentialsList.size(); i++) {
			WebElement element = credentialsList.get(i);
			if (element.getAttribute("innerHTML").equals(credURL)) {
				isAdd = true;
				break;
			}
		}
		Assertions.assertTrue(isAdd);
	}

	@Test
	public void credentialEditSuperDuper() {
		String credURL = "http://localhost:8080/edit";
		String userName = "credentialEdit";
		doMockSignUp(firstName,lastName,userName,password);
		doLogIn(userName, password);
		credentialMock(credURL, userName, password);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		// edit credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTableBody")));
		WebElement credentialsTable = driver.findElement(By.id("credentialTableBody"));
		List<WebElement> credentialsListDetails = credentialsTable.findElements(By.tagName("th"));
		WebElement parent = null;
		for(int i=0; i < credentialsListDetails.size(); i++) {
			WebElement element = credentialsListDetails.get(i);
			if (element.getAttribute("innerHTML").equals(credURL)) {
				parent = (WebElement) ((JavascriptExecutor) driver).executeScript(
						"return arguments[0].parentNode;", element);
			}
		}
		List<WebElement> credentialsList = parent.findElements(By.tagName("td"));
		WebElement editElement = null;
		for(int i=0; i < credentialsList.size(); i++) {
			WebElement element = credentialsList.get(i);
			editElement = element.findElement(By.name("edit"));
			if (editElement != null) {
				break;
			}
		}
		editElement.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement credentialURL = driver.findElement(By.id("credential-url"));
		credentialURL.clear();
		credentialURL.sendKeys(newcredURL);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credential")));
		WebElement saveButton = driver.findElement(By.id("save-credential"));
		saveButton.click();

		//webDriverWait.until(ExpectedConditions.titleContains("Result"));
		Assertions.assertEquals("Result", driver.getTitle());

		// check credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-link")));
		WebElement homeButton = driver.findElement(By.id("success-link"));
		homeButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTableBody")));
		credentialsTable = driver.findElement(By.id("credentialTableBody"));
		credentialsList = credentialsTable.findElements(By.tagName("th"));
		Boolean isEdit = false;
		for(int i=0; i < credentialsList.size(); i++) {
			WebElement element = credentialsList.get(i);
			if (element.getAttribute("innerHTML").equals(newcredURL)) {
				isEdit = true;
				break;
			}
		}
		Assertions.assertTrue(isEdit);
	}

	@Test
	public void credentialDeleteSuperDuper() {
		String credURL = "http://localhost:8080/delete";
		String userName = "credentialDelete";
		doMockSignUp(firstName,lastName,userName,password);
		doLogIn(userName, password);
		credentialMock(credURL, userName, password);

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		// delete credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTableBody")));
		WebElement credentialsTable = driver.findElement(By.id("credentialTableBody"));
		List<WebElement> credentialsListDetails = credentialsTable.findElements(By.tagName("td"));
		WebElement parent = null;
		for(int i=0; i < credentialsListDetails.size(); i++) {
			WebElement element = credentialsListDetails.get(i);
			if (element.getAttribute("innerHTML").equals(userName)) {
				parent = (WebElement) ((JavascriptExecutor) driver).executeScript(
						"return arguments[0].parentNode;", element);
			}
		}
		List<WebElement> credentialsList = parent.findElements(By.tagName("td"));
		WebElement deleteElement = null;
		for(int i=0; i < credentialsList.size(); i++) {
			WebElement element = credentialsList.get(i);
			deleteElement = element.findElement(By.name("delete"));
			if (deleteElement != null) {
				break;
			}
		}
		deleteElement.click();

		//webDriverWait.until(ExpectedConditions.titleContains("Result"));
		Assertions.assertEquals("Result", driver.getTitle());

		// check Credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-link")));
		WebElement homeButton = driver.findElement(By.id("success-link"));
		homeButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTableBody")));
		credentialsTable = driver.findElement(By.id("credentialTableBody"));
		credentialsList = credentialsTable.findElements(By.tagName("td"));
		Boolean isDelete = true;
		for(int i=0; i < credentialsList.size(); i++) {
			WebElement element = credentialsList.get(i);
			if (element.getAttribute("innerHTML").equals(userName)) {
				isDelete = false;
				break;
			}
		}
		Assertions.assertTrue(isDelete);
	}

	private void noteMock(String title, String description) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		// added note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-note")));
		WebElement newNote = driver.findElement(By.id("new-note"));
		newNote.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement noteTitle = driver.findElement(By.id("note-title"));
		noteTitle.sendKeys(title);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement noteDescription = driver.findElement(By.id("note-description"));
		noteDescription.sendKeys(description);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-note")));
		WebElement saveButton = driver.findElement(By.id("save-note"));
		saveButton.click();

		//webDriverWait.until(ExpectedConditions.titleContains("Result"));
		Assertions.assertEquals("Result", driver.getTitle());

		// check note
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-link")));
		WebElement homeButton = driver.findElement(By.id("success-link"));
		homeButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		jse.executeScript("arguments[0].click()", notesTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTableBody")));
		WebElement notesTable = driver.findElement(By.id("userTableBody"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("th"));
		Boolean isAdd = false;
		for(int i=0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals(title)) {
				isAdd = true;
				break;
			}
		}
		Assertions.assertTrue(isAdd);
	}

	private void credentialMock(String credURL, String userName, String password) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		driver.get("http://localhost:" + this.port + "/home");
		WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		// added credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("new-credential")));
		WebElement newCredential = driver.findElement(By.id("new-credential"));
		newCredential.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement credentialUrl = driver.findElement(By.id("credential-url"));
		credentialUrl.sendKeys(credURL);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement credentialUsername = driver.findElement(By.id("credential-username"));
		credentialUsername.sendKeys(userName);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement credentialPassword = driver.findElement(By.id("credential-password"));
		credentialPassword.sendKeys(password);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("save-credential")));
		WebElement saveButton = driver.findElement(By.id("save-credential"));
		saveButton.click();

		//webDriverWait.until(ExpectedConditions.titleContains("Result"));
		Assertions.assertEquals("Result", driver.getTitle());

		// check Credential
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-link")));
		WebElement homeButton = driver.findElement(By.id("success-link"));
		homeButton.click();
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credentialTab);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTableBody")));
		WebElement credentialsTable = driver.findElement(By.id("credentialTableBody"));
		List<WebElement> credentialsList = credentialsTable.findElements(By.tagName("th"));
		Boolean isAdd = false;
		for(int i=0; i < credentialsList.size(); i++) {
			WebElement element = credentialsList.get(i);
			if (element.getAttribute("innerHTML").equals(credURL)) {
				isAdd = true;
				break;
			}
		}
		Assertions.assertTrue(isAdd);
	}
}