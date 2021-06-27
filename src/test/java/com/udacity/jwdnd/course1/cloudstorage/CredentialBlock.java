package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CredentialBlock {

	
	private final WebDriver driver;
	
    @FindBy(id = "nav-credentials-tab")
    private WebElement credTab;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credUrlText")
    private WebElement credentialUrlText;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credUsernameText")
    private WebElement credentialUsernameText;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credPasswordText")
    private WebElement credentialPasswordText;

    @FindBy(id = "add-cred-btn")
    private WebElement addCredButton;

    @FindBy(id = "cred-EditBtn")
    private WebElement editButton;

    @FindBy(id = "cred-DeleteBtn")
    private WebElement deleteButton;

    @FindBy(id = "cred-savechanges-btn")
    private WebElement submitButton;

    @FindBy(id = "success-message-cred")
    private WebElement successMessage;

    public CredentialBlock(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void clickCredTab() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.credTab);
    }
    
    public void clickAddCredBtn() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.addCredButton);
    }

    public void setCredentialData(String url, String username, String password) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + url + "';", this.credentialUrl);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", this.credentialUsername);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", this.credentialPassword);
    }

    public void clickToSave() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.submitButton);
    }
    
    public void clickToEdit(int pos) {
        List <WebElement> editButtons = driver.findElements(By.id("cred-EditBtn"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButtons.get(pos - 1));
    }

    public void clickToDelete() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.deleteButton);
    }

    public String getUrlText(int pos) {
        List<WebElement> urls = driver.findElements(By.id("credUrlText"));
        return urls.get(pos-1).getAttribute("innerHTML");
    }

    public String getUsernameText(int pos) {
        List<WebElement> urls = driver.findElements(By.id("credUsernameText"));
        return urls.get(pos-1).getAttribute("innerHTML");
    }

    public String getPasswordText(int pos) {
        List<WebElement> urls = driver.findElements(By.id("credPasswordText"));
        return urls.get(pos-1).getAttribute("innerHTML");
    }

    public boolean getSuccessMessage() {
        return this.successMessage.isDisplayed();
    }
}
