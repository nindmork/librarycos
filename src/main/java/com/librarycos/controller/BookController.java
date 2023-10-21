package com.librarycos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.librarycos.ControllerHelper;
import com.librarycos.entity.Book;

import com.librarycos.entity.User;
import com.librarycos.service.BookService;
import com.librarycos.service.RentalService;

import jakarta.servlet.http.HttpServletRequest;

import java.text.ParseException;
import java.util.*;

@Controller
public class BookController {
	
	@Autowired private BookService bservice;		
	@Autowired private ControllerHelper controllerHelper;
	@Autowired private RentalService rentalService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/book_register")
	public String bookRegister(Model model) {
		Book book =  new Book();
		model.addAttribute("book",book);
		model.addAttribute("pageTitle","แก้ไขหนังสือ");
		return "book_form";
	}
	
	@GetMapping("/available_books")
	public String getAllBook(Model model) throws ParseException {
		return listByPage(1,model,"name","asc", null);
	}
	
	
	@GetMapping("/available_books/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum")int pageNum,Model model, 
			@Param("sortField")String sortField, 
			@Param("sortDir")String sortDir,
			@Param("keyword")String keyword) throws ParseException {
		rentalService.updateRentalExpired();
		Page<Book> page = bservice.listByPage(pageNum, sortField, sortDir, keyword);
		List<Book> listBook = page.getContent();
		long startCount = (pageNum - 1) * BookService.BOOK_PER_PAGE + 1;
		long endCount = startCount + BookService.BOOK_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc"; 
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("book", listBook);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		return "bookList";
	}
	

		
	@PostMapping("/book/save")
	public String addBook(@ModelAttribute Book b,HttpServletRequest request) {
		User user = controllerHelper.getAuthenticatedUser(request);
		bservice.save(b,user);
		return "redirect:/available_books";
	}

	@GetMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") int id,Model model) {
		Book b = bservice.getBookById(id);
		model.addAttribute("book",b);
		model.addAttribute("pageTitle","แก้ไขหนังสือ");
		return "book_form";
	}
	
	@GetMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id")int id) {
		bservice.deleteById(id);
		return "redirect:/available_books";
	}
	
}
