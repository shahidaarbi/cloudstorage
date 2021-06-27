package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	
	private final WebDriver driver;

	@FindBy(css = "#inputUsername")
	private WebElement userNameInput;

	@FindBy(css = "#inputPassword")
	private WebElement passwordInput;

	@FindBy(css = "#login-btn")
	private WebElement loginButton;

	@FindBy(css = "#logout-msg")
	private WebElement logoutMessage;

	@FindBy(css = "#error-msg")
	private WebElement errorMessage;

	public LoginPage(WebDriver webDriver) {
		this.driver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void clickToLogin() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.loginButton);
	}

	public void setLoginData(String userName, String password) {
		this.userNameInput.sendKeys(userName);
		this.passwordInput.sendKeys(password);
	}

	public String getUsernameInput() {
		return userNameInput.getAttribute("value");
	}

	public String getPasswordInput() {
		return passwordInput.getAttribute("value");
	}

	public boolean getErrorMessage() {
		return this.errorMessage.isDisplayed();
	}

	public boolean getLogoutMessage() {
		return this.logoutMessage.isDisplayed();
	}

}
