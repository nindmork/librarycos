package com.bookStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bookStore.entity.Book;

import com.bookStore.service.BookService;


import java.util.*;

@Controller
public class BookController {
	
	@Autowired private BookService bservice;		

	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/book_register")
	public String bookRegister() {
		return "bookRegister";
	}
	
	@GetMapping("/available_books")
	public ModelAndView getAllBook() {
		List<Book>list = bservice.getAllBook();
//		ModelAndView m=new ModelAndView();
//		m.setViewName("bookList");
//		m.addObject("book",list);
		return new ModelAndView("bookList","book",list);
	}
	
	
	

	
	@PostMapping("/save")
	public String addBook(@ModelAttribute Book b) {
		bservice.save(b);
		return "redirect:/available_books";
	}

	@RequestMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") int id,Model model) {
		Book b = bservice.getBookById(id);
		model.addAttribute("book",b);
		return "bookEdit";
	}
	
	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id")int id) {
		bservice.deleteById(id);
		return "redirect:/available_books";
	}
	
}
