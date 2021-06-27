package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

	
	private final WebDriver driver;

	@FindBy(css = "#inputFirstName")
	private WebElement firstNameInput;

	@FindBy(css = "#inputLastName")
	private WebElement lastNameInput;

	@FindBy(css = "#inputUsername")
	private WebElement usernameInput;

	@FindBy(css = "#inputPassword")
	private WebElement passwordInput;

	@FindBy(css = "#success-msg")
	private WebElement successMessage;

	@FindBy(css = "#error-msg")
	private WebElement errorMessage;

	@FindBy(css = "#submit-button")
	private WebElement signUpButton;

	public SignupPage(WebDriver webDriver) {
		this.driver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void setSignUpData(String firstName, String lastName, String username, String password) {
		this.firstNameInput.sendKeys(firstName);
		this.lastNameInput.sendKeys(lastName);
		this.usernameInput.sendKeys(username);
		this.passwordInput.sendKeys(password);
	}

	public String getFirstNameInput() {
		return firstNameInput.getAttribute("value");
	}

	public String getLastNameInput() {
		return lastNameInput.getAttribute("value");
	}

	public String getUsernameField() {
		return usernameInput.getAttribute("value");
	}

	public String getPasswordField() {
		return passwordInput.getAttribute("value");
	}

	public boolean getSuccessMessage() {
		return this.successMessage.isDisplayed();
	}

	public boolean getErrorMessage() {
		return this.errorMessage.isDisplayed();
	}

	public WebElement getSubmitButton() {
		return signUpButton;
	}

	public void clickToSignUp() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", signUpButton);
	}

}
