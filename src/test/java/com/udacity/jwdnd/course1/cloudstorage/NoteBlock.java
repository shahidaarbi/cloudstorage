package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NoteBlock {

	
	private final WebDriver driver;

	@FindBy(id = "nav-notes-tab")
	private WebElement noteTab;

	@FindBy(id = "note-title")
	private WebElement noteTitle;

	@FindBy(id = "noteTitleText")
	private WebElement noteTitleText;

	@FindBy(id = "note-description")
	private WebElement noteDescription;

	@FindBy(id = "noteDescriptionText")
	private WebElement noteDescriptionText;

	@FindBy(id = "add-note-btn")
	private WebElement addNoteButton;

	@FindBy(id = "note-save-button")
	private WebElement saveNoteButton;

	@FindBy(id = "note-editBtn")
	private WebElement editNoteButton;

	@FindBy(id = "note-savechanges-btn")
	private WebElement saveEditNoteButton;

	@FindBy(id = "note-deleteBtn")
	private WebElement deleteNoteButton;

	@FindBy(id = "success-message-note")
	private WebElement successMessage;

	public NoteBlock(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickNoteTab() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.noteTab);
	}

	public void clickToAddNote() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.addNoteButton);
	}

	public void addNote(String title, String description) {
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", this.noteTitle);
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", this.noteDescription);
	}

	public void clickToSaveNote() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.saveNoteButton);
	}

	public void clickToEdit() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.editNoteButton);
	}

	public void updateNote(String title, String description) {
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + title + "';", this.noteTitle);
		((JavascriptExecutor) driver).executeScript("arguments[0].value='" + description + "';", this.noteDescription);
	}

	public void clickToDelete() {
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", this.deleteNoteButton);
	}

	public String getNoteTitleText() {
		return noteTitleText.getAttribute("innerHTML");
	}

	public String getNoteDescriptionText() {
		return noteDescriptionText.getAttribute("innerHTML");
	}

	public boolean getSuccessMessage() {
		return this.successMessage.isDisplayed();
	}

}
