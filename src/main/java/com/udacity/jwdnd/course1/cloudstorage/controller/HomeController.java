package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

	private NoteService noteService;
	private CredentialService credentialService;
	private FileService fileService;
	private UserService userService;
    private EncryptionService encryptionService;

    @Autowired
	public HomeController(NoteService noteService, CredentialService credentialService, FileService fileService,
			UserService userService, EncryptionService encryptionService) {
		this.noteService = noteService;
		this.credentialService = credentialService;
		this.fileService = fileService;
		this.userService = userService;
		this.encryptionService = encryptionService;
	}

	@GetMapping()
	public String getHome(Model model, Authentication authentication) {
		Integer currentUser = getCurrentUser(authentication);
		model.addAttribute("files", fileService.getAllFiles(currentUser));
		model.addAttribute("notes", noteService.getNotes(currentUser));
		model.addAttribute("credentials", credentialService.getCredentials(currentUser));
		model.addAttribute("encryptionService", encryptionService);
		return "/home";
	}

	@PostMapping("/note")
	public String addNote(@ModelAttribute Note note, RedirectAttributes redirectAttributes,
			Authentication authentication) {
		try {
			noteService.addNote(note, getCurrentUser(authentication));
			redirectAttributes.addFlashAttribute("noteSuccess", true);

			if (null == note.getNoteid()) {
				redirectAttributes.addFlashAttribute("noteMessage", "Note added: " + note.getNotetitle());
			} else {
				redirectAttributes.addFlashAttribute("noteMessage", "Note updated: " + note.getNotetitle());
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("noteError", true);
			redirectAttributes.addFlashAttribute("noteMessage", "Error while processing note!");
		}
		return "redirect:/home";

	}

	@GetMapping("/note/delete/{id}")
	public String deleteNote(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes,
			Authentication authentication) {
		String deletedNote = null;
		try {
			Note note = noteService.getNoteByNoteId(id);
			deletedNote = note.getNotetitle();
			noteService.deleteNote(id);
			redirectAttributes.addFlashAttribute("noteSuccess", true);
			redirectAttributes.addFlashAttribute("noteMessage", "Deleted note: " + deletedNote);

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("noteError", true);
			redirectAttributes.addFlashAttribute("noteMessage", "Error while deleting note: " + deletedNote);
		}

		return "redirect:/home";
	}

	@PostMapping("/credential")
	public String addCredential(@ModelAttribute Credential credential, RedirectAttributes redirectAttributes,
			Authentication authentication) {

		try {
			if (null == credential.getCredentialid()) {
				credentialService.addCredential(credential, getCurrentUser(authentication));
				redirectAttributes.addFlashAttribute("credSuccess", true);
				redirectAttributes.addFlashAttribute("credMessage", "Credential added: " + credential.getUrl());
			} else {
				credentialService.addCredential(credential, getCurrentUser(authentication));
				redirectAttributes.addFlashAttribute("credSuccess", true);
				redirectAttributes.addFlashAttribute("credMessage",
						"Credential updated: " + credential.getUrl());
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("credError", true);
			redirectAttributes.addFlashAttribute("credMessage", "Error while processing credential!");
		}
		return "redirect:/home";
	}

	@GetMapping("/credential/delete/{id}")
	public String deleteCredential(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes,
			Authentication authentication) {

		String deletedCred = null;
		try {
			Credential credential = credentialService.getCredentialById(id);
			deletedCred = credential.getUrl();
			credentialService.deleteCredential(id);
			redirectAttributes.addFlashAttribute("credSuccess", true);
			redirectAttributes.addFlashAttribute("credMessage", "Credential deleted: " + deletedCred);

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("credError", true);
			redirectAttributes.addFlashAttribute("credMessage",
					"Error while deleting credential: " + deletedCred);
		}
		return "redirect:/home";
	}

	@PostMapping("/file")
	public String addFile(@RequestParam("fileUpload") MultipartFile file, RedirectAttributes redirectAttributes,
			Authentication authentication) {

		try {

			if (file.isEmpty() || file.getSize() > 5000000) {
				redirectAttributes.addFlashAttribute("fileError", true);
				redirectAttributes.addFlashAttribute("fileMessage", "File upload error: Invalid file, maximum size 5MB");
				return "redirect:/home";
			}

			if (fileService.isDuplicate(file.getOriginalFilename(), getCurrentUser(authentication))) {
				redirectAttributes.addFlashAttribute("fileError", true);
				redirectAttributes.addFlashAttribute("fileMessage", "File upload: Same file already uploaded");
				return "redirect:/home";
			}

			if (fileService.loadFile(file, getCurrentUser(authentication)) > 0) {
				redirectAttributes.addFlashAttribute("fileSuccess", true);
				redirectAttributes.addFlashAttribute("fileMessage", "File uploaded: " + file.getOriginalFilename());
			} else {
				redirectAttributes.addFlashAttribute("fileError", true);
				redirectAttributes.addFlashAttribute("fileMessage", "File upload failed: " + file.getOriginalFilename());
			}
		} catch (IOException ioException) {
			redirectAttributes.addFlashAttribute("fileError", true);
			redirectAttributes.addFlashAttribute("fileMessage", "Error while uploading file!");
		}

		return "redirect:/home";
	}

	@GetMapping("/file/delete/{id}")
	public String deleteFile(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes,
			Authentication authentication) {

		String fileName = null;

		try {
			File file = fileService.getFileById(id);
			fileName = file.getFileName();
			fileService.deleteFile(id, getCurrentUser(authentication));
			redirectAttributes.addFlashAttribute("fileSuccess", true);
			redirectAttributes.addFlashAttribute("fileMessage", "Deleted file: " + fileName);

		} catch (Exception e) {

			redirectAttributes.addFlashAttribute("fileError", true);
			redirectAttributes.addFlashAttribute("fileMessage", "Error while deleting file!");
		}
		return "redirect:/home";
	}

	@GetMapping("/file/view/{id}")
	public ResponseEntity<Object> viewFile(@PathVariable("id") Integer id) {
		File file = fileService.getFileById(id);
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
				.body(file.getFileData());
	}

	private Integer getCurrentUser(Authentication authentication) {
		return userService.getUser(authentication.getName()).getUserId();
	}

}
