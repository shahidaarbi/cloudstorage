package com.udacity.jwdnd.course1.cloudstorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.github.bonigarcia.wdm.WebDriverManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;
	private static WebDriver driver;

	private String basePath = null;
	String userNameFail = "b";
	String passwordFail = "b";

	String firstName = "Tom";
	String lastName = "Bob";
	String userName = "a";
	String password = "a";

	String noteTitle = "Note Title";
	String noteDescription = "Note Description";
	
	String credUrl1 = "abc.com";
	String credUsername1 = "a";
	String credPassword1 = "a";
	
	String credUrl2 = "xzy.com";
	String credUsername2 = "b";
	String credPassword2 = "b";
	
	String credUrl3 = "dummy.com";
	String credUsername3 = "c";
	String credPassword3 = "c";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();

	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		basePath = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get(basePath + "/login");
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testLoginFail() throws InterruptedException {
		getLoginPage();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.setLoginData(userNameFail, passwordFail);
		assertEquals(userNameFail, loginPage.getUsernameInput());
		assertEquals(passwordFail, loginPage.getPasswordInput());
		Thread.sleep(2000);
		loginPage.clickToLogin();
		assertEquals("Login", driver.getTitle());
		assertTrue(loginPage.getErrorMessage());
		Thread.sleep(2000);
	}

	@Test
	public void testSignupSuccess() throws InterruptedException {
		driver.get(basePath + "/signup");
		SignupPage signupBlock = new SignupPage(driver);
		signupBlock.setSignUpData(firstName, lastName, userName, password);
		Thread.sleep(2000);
		signupBlock.clickToSignUp();
		Thread.sleep(2000);
		assertEquals("Sign Up", driver.getTitle());
		assertTrue(signupBlock.getSuccessMessage());
	}

	@Test
	public void testSignUpAndLoginPass() throws InterruptedException {
		testSignupSuccess();
		
		driver.get(basePath + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setLoginData(userName, password);
		assertEquals(userName, loginPage.getUsernameInput());
		assertEquals(password, loginPage.getPasswordInput());
		Thread.sleep(2000);
		loginPage.clickToLogin();
		assertEquals("Home", driver.getTitle());
	}
	
	@Test
	public void testLogout()throws InterruptedException {
		testSignUpAndLoginPass();
		
		HomePage homePage = new HomePage(driver);
		Thread.sleep(2000);
		homePage.clickToLogout();
		assertEquals("Login", driver.getTitle());
		Thread.sleep(4000);
		
		driver.get(basePath + "/home");
		Thread.sleep(2000);
		assertEquals("Login", driver.getTitle());
	}
	
	public void testLogoutForIntegration()throws InterruptedException {
		HomePage homePage = new HomePage(driver);
		Thread.sleep(2000);
		homePage.clickToLogout();
		assertEquals("Login", driver.getTitle());
		Thread.sleep(4000);
		
		driver.get(basePath + "/home");
		Thread.sleep(2000);
		assertEquals("Login", driver.getTitle());
	}

	@Test
	public void testAddNote() throws InterruptedException {
		testSignUpAndLoginPass();

		NoteBlock notesBlock = new NoteBlock(driver);
		notesBlock.clickNoteTab();
		Thread.sleep(2000);
		notesBlock.clickToAddNote();
		Thread.sleep(2000);
		notesBlock.addNote(noteTitle, noteDescription);
		Thread.sleep(2000);
		notesBlock.clickToSaveNote();
		assertEquals(noteTitle, notesBlock.getNoteTitleText());
		assertEquals(noteDescription, notesBlock.getNoteDescriptionText());
		Thread.sleep(2000);
		assertTrue(notesBlock.getSuccessMessage());
		Thread.sleep(2000);
	}
	
	
	@Test
	public void testUpdateNote() throws InterruptedException{
		testAddNote();

		NoteBlock notesBlock = new NoteBlock(driver);
		notesBlock.clickNoteTab();
		Thread.sleep(2000);
		notesBlock.clickToEdit();
		Thread.sleep(2000);
		notesBlock.updateNote(noteTitle+" updated", noteDescription+" updated");
		Thread.sleep(2000);
		notesBlock.clickToSaveNote();
		Thread.sleep(2000);
		assertEquals(noteTitle+" updated", notesBlock.getNoteTitleText());
		assertEquals(noteDescription+" updated", notesBlock.getNoteDescriptionText());
		assertTrue(notesBlock.getSuccessMessage());
	}
	
	public void testUpdateNoteForIntegration() throws InterruptedException{
		NoteBlock notesBlock = new NoteBlock(driver);
		notesBlock.clickNoteTab();
		Thread.sleep(2000);
		notesBlock.clickToEdit();
		Thread.sleep(2000);
		notesBlock.updateNote(noteTitle+" updated", noteDescription+" updated");
		Thread.sleep(2000);
		notesBlock.clickToSaveNote();
		Thread.sleep(2000);
		assertEquals(noteTitle+" updated", notesBlock.getNoteTitleText());
		assertEquals(noteDescription+" updated", notesBlock.getNoteDescriptionText());
		assertTrue(notesBlock.getSuccessMessage());
	}
	
	
	@Test
	public void testDeleteNote() throws InterruptedException{
		testAddNote();
		
		NoteBlock notesBlock = new NoteBlock(driver);
		Thread.sleep(2000);
		notesBlock.clickToDelete();
		Thread.sleep(2000);
		assertTrue(notesBlock.getSuccessMessage());

	}
	
	@Test
	public void testDeleteNoteForIntegration() throws InterruptedException{
		NoteBlock notesBlock = new NoteBlock(driver);
		Thread.sleep(2000);
		notesBlock.clickToDelete();
		Thread.sleep(2000);
		assertTrue(notesBlock.getSuccessMessage());

	}
	
	@Test
	public void testAddCredential() throws InterruptedException {
		testSignUpAndLoginPass();
		
		CredentialBlock credentialsBlock = new CredentialBlock(driver);
		credentialsBlock.clickCredTab();
		Thread.sleep(2000);
		
		createCredential(credentialsBlock, credUrl1, credUsername1, credPassword1);
		createCredential(credentialsBlock, credUrl2, credUsername2, credPassword2);
		createCredential(credentialsBlock, credUrl3, credUsername3, credPassword3);
	}
	
	public void testAddCredentialForIntegration() throws InterruptedException {
		CredentialBlock credentialsBlock = new CredentialBlock(driver);
		credentialsBlock.clickCredTab();
		Thread.sleep(2000);
		
		createCredential(credentialsBlock, credUrl1, credUsername1, credPassword1);
		createCredential(credentialsBlock, credUrl2, credUsername2, credPassword2);
		createCredential(credentialsBlock, credUrl3, credUsername3, credPassword3);
	}
	
	private void createCredential(CredentialBlock credentialsBlock, String url, String username, String password  ) throws InterruptedException{
		credentialsBlock.clickAddCredBtn();
		Thread.sleep(2000);

		credentialsBlock.setCredentialData(url , username, password);
		Thread.sleep(2000);
		
		credentialsBlock.clickToSave();
		Thread.sleep(1000);
		
		assertTrue(credentialsBlock.getSuccessMessage());
		Thread.sleep(1000);
	}
	
	
	@Test
	public void testUpdateCredential() throws InterruptedException {
		testAddCredential();
		
		CredentialBlock credentialsBlock = new CredentialBlock(driver);
		credentialsBlock.clickToEdit(1);
		Thread.sleep(2000);
		
		credentialsBlock.setCredentialData(credUrl1, "user", credPassword1);
		Thread.sleep(2000);

		credentialsBlock.clickToSave();
		Thread.sleep(2000);
		
		assertEquals("user", credentialsBlock.getUsernameText(1));
		assertTrue(credentialsBlock.getSuccessMessage());
		
		Thread.sleep(2000);
		
	}
	
	@Test
	public void testUpdateCredentialForIntegration() throws InterruptedException {
		
		CredentialBlock credentialsBlock = new CredentialBlock(driver);
		credentialsBlock.clickToEdit(1);
		Thread.sleep(2000);
		
		credentialsBlock.setCredentialData(credUrl1, "user", credPassword1);
		Thread.sleep(2000);

		credentialsBlock.clickToSave();
		Thread.sleep(2000);
		
		assertEquals("user", credentialsBlock.getUsernameText(1));
		assertTrue(credentialsBlock.getSuccessMessage());
		
		Thread.sleep(2000);
		
	}

	
	
	@Test
	public void testDeleteCredential()throws InterruptedException {
		testAddCredential();
		
		Thread.sleep(2000);
		CredentialBlock credentialsBlock = new CredentialBlock(driver);
		credentialsBlock.clickCredTab();
		Thread.sleep(2000);
		
		credentialsBlock.clickToDelete();
		Thread.sleep(2000);
		
		assertTrue(credentialsBlock.getSuccessMessage());

	}
	
	public void testDeleteCredentialForIntegraion()throws InterruptedException {
		
		Thread.sleep(2000);
		CredentialBlock credentialsBlock = new CredentialBlock(driver);
		credentialsBlock.clickCredTab();
		Thread.sleep(2000);
		
		credentialsBlock.clickToDelete();
		Thread.sleep(2000);
		
		assertTrue(credentialsBlock.getSuccessMessage());

	}
	
	
	@Test
	public void applicationTest() throws InterruptedException{
		testAddNote();
		testUpdateNoteForIntegration();
		testDeleteNoteForIntegration();
		
		testAddCredentialForIntegration();
		testUpdateCredentialForIntegration();
		testDeleteCredentialForIntegraion();
		
		testLogoutForIntegration();
		
	}
	

}
