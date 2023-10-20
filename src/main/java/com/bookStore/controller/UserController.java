package com.bookStore.controller;


import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.bookStore.entity.Role;
import com.bookStore.entity.User;
import com.bookStore.security.BookUserDetails;

import com.bookStore.service.UserNotFoundException;
import com.bookStore.service.UserService;



@Controller
public class UserController {
	
	@Autowired
 	private UserService service;
	

	
	@GetMapping("/login")
	public String viewloginPage() {
		return "login";
	}
	

	
	@GetMapping("/users")
	public String listFirstPage(Model model) {
		/*List<User> listUsers = service.listAll();
		model.addAttribute("users", listUsers);
		return "users";*/
		return listByPage(1,model);
		
	}
	
	@GetMapping("/users/new")
	public String newUser(Model model) {
		List<Role> listRoles = service.listRoles();
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("user",user);
		model.addAttribute("listRoles",listRoles);
		model.addAttribute("pageTitle","Create New User");
		return "user_form";
	}
	
	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum")int pageNum,Model model) {
		Page<User> page = service.listByPage(pageNum);
		List<User> listUsers = page.getContent();
		
		/*System.out.println("Pagenum = " + pageNum);
		System.out.println("Total element = " + page.getTotalElements());
		System.out.println("Total page = " + page.getTotalPages());*/
		
		long startCount = (pageNum - 1) * UserService.USERS_PER_PAGE + 1;
		long endCount = startCount + UserService.USERS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("users", listUsers);
		return "users";
	}
	
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes
			) throws IOException {

		redirectAttributes.addFlashAttribute("message", "The user has been saved sucessfully.");

		service.save(user);
		return "redirect:/users";
	}
	
	private String getRedirectURLtoAffectedUser(User user) {
		String firstPartOfEmail = user.getEmail().split("@")[0];
		return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword=" + firstPartOfEmail;
	}
	
	
	
	
	@PostMapping("/account/update")
	public String saveDetails(User user, RedirectAttributes redirectAttributes,
			BookUserDetails loggedUser,
			@RequestParam("image") MultipartFile multipartFile) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = service.updateAccount(user);
			//String uploadDir = "user-photos/" + savedUser.getId();
			//FileUploadUtil.cleanDir(uploadDir);
		//	FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}else {
			if (user.getPhotos().isEmpty()) user.setPhotos(null);
			service.updateAccount(user);
		}
		loggedUser.setFirstName(user.getFirstName());
		loggedUser.setLastName(user.getLastName());
		redirectAttributes.addFlashAttribute("message", "Your account details have been updated.");
		return "redirect:/account";
		
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name ="id") Integer id ,
		Model model, 
		RedirectAttributes redirectAttributes) {
			try {
				User user = service.get(id);
				List<Role> listRoles = service.listRoles();
				model.addAttribute("user",user);
				model.addAttribute("pageTitle","Edit User(ID: " + id + ")");;
				model.addAttribute("listRoles",listRoles);
				return "user_form" ;
			}catch (UserNotFoundException ex) {
				redirectAttributes.addFlashAttribute("message", ex.getMessage());
				return "redirect:/users";
			}
		}
	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable(name ="id") Integer id ,
		Model model, 
		RedirectAttributes redirectAttributes) {
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message","The user ID" +id+ "has been deleted sucessfully");
		}catch (UserNotFoundException ex) {
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
			
		}return "redirect:/users";
	}
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserEnabledStatus(@PathVariable("id") Integer id,
			@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
		service.updateUserEnabledStatus(id, enabled);
		String status = enabled ? "enabled" : "disabled";
		String message = "The user ID " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/users";
				
	}
	


}

