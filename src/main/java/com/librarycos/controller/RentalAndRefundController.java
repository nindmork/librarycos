package com.librarycos.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.librarycos.ControllerHelper;
import com.librarycos.entity.Book;
import com.librarycos.entity.BookStatus;
import com.librarycos.entity.Customers;
import com.librarycos.entity.Rental;
import com.librarycos.entity.User;
import com.librarycos.service.BookService;
import com.librarycos.service.CustomerService;
import com.librarycos.service.RentalService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Controller
public class RentalAndRefundController {

	@Autowired private BookService bservice;
	@Autowired private CustomerService customerService;	
	@Autowired private RentalService rentalService; 
	@Autowired private ControllerHelper controllerHelper;
	
	@GetMapping("/find_customer")
	public String getAllCustomer(Model model) {
		
		return listByPagefindCustomer(1,model,"firstname","asc", null);
	}
	
	@GetMapping("/find_customer/page/{pageNum}")
	public String listByPagefindCustomer(@PathVariable(name = "pageNum")int pageNum,Model model, 
			@Param("sortField")String sortField, 
			@Param("sortDir")String sortDir,
			@Param("keyword")String keyword) {
		Page<Customers> page = customerService.listByPage(pageNum, sortField, sortDir, keyword);
		List<Customers> listCustomers = page.getContent();
		long startCount = (pageNum - 1) * CustomerService.CUSTOMERS_PER_PAGE + 1;
		long endCount = startCount + CustomerService.CUSTOMERS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc"; 
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("customers", listCustomers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		return "findCustomer";
	}
	
	
	
	@GetMapping("/book_rental/{customerid}/page/{pageNum}")
	public String listByPage(@PathVariable(name = "customerid")String customerId,@PathVariable(name = "pageNum")int pageNum,Model model, 
			@Param("sortField")String sortField, 
			@Param("sortDir")String sortDir,
			@Param("keyword")String keyword
			) {
		Page<Book> page = bservice.listByPage(pageNum, sortField, sortDir, keyword);
		List<Book> listBook = page.getContent();
		List<Book> readylistBook = new ArrayList<>();
		for (Book book : listBook) {
			if (book.getStatus() != BookStatus.ถูกยืมแล้ว) {
				readylistBook.add(book);
			}
		}
		
		long startCount = (pageNum - 1) * BookService.BOOK_PER_PAGE + 1;
		long endCount = startCount + BookService.BOOK_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc"; 
		int cusid= Integer.valueOf(customerId);
		Customers customer = customerService.getById(cusid);
		
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("customer", customer);
		model.addAttribute("book", readylistBook);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		return "bookrental";
	
	}
	
	
	@GetMapping("/book_rental/{customerid}")
	public String getAllBookrental(Model model, HttpServletResponse response,@PathVariable("customerid")String customerId) {
		/// create a cookie
	    Cookie cookie = new Cookie("customerid", customerId);
	    cookie.setPath("/cartview");
	    //add a cookie to the response
	    //cookie.setMaxAge(7);
	    response.addCookie(cookie);	
	    //ลบbookCookieเก่า
	    Cookie bookidcookie = new Cookie("bookIdCook","");
	    bookidcookie.setValue(null);
	    response.addCookie(bookidcookie);
	    return listByPage(customerId,1,model,"name","asc", null );
	}
	
	@GetMapping("/cartview/{bookid}")
	public String ViewCart(Model model,HttpServletResponse response,@CookieValue(value = "customerid", defaultValue = "") String customerId,@PathVariable("bookid")String bookId) throws ParseException {
		rentalService.updateRentalExpired();
		Integer book_id = Integer.valueOf(bookId);
		Book book = bservice.getBookById(book_id);
		Integer customerIdi = Integer.valueOf(customerId);
	    Customers customer = customerService.getById(customerIdi);
		if (book.getStatus() == BookStatus.ถูกยืมแล้ว) {
			return "/book_rental/"+ customerId;
		}
		else {
			Cookie bookidcookie = new Cookie("bookIdCook", bookId);
			Cookie customerIdcookie = new Cookie("customerIdCook", customerId);
			bookidcookie.setPath("/place_order");
			customerIdcookie.setPath("/place_order");
		    //add a cookie to the response
		    //cookie.setMaxAge(7);	
		    response.addCookie(bookidcookie);
		    response.addCookie(customerIdcookie);
		    Rental rental = new Rental();
		    rental.setRentalTime(new Date());  
		    rental.setRentaEndtime(rentalService.calculateRentalEndTime(new Date(),book.getDayofrents())); 
		    
		    model.addAttribute("book",book);
			model.addAttribute("customer",customer);
			model.addAttribute("rental",rental);
		}
		return "/cartview";
	}
	
	
	
	@GetMapping("/place_order")
	public String placeOrder(Model model,HttpServletRequest request,
			HttpServletResponse response,
			@CookieValue(value = "customerIdCook", defaultValue = "") String customerid,
			@CookieValue(value = "bookIdCook", defaultValue = "") String bookid) throws ParseException {
		rentalService.updateRentalExpired();
		int bookidI = Integer.valueOf(bookid);
		int customeridI = Integer.valueOf(customerid);
		Book book = bservice.getBookById(bookidI);
		if (book.getStatus() == BookStatus.ถูกยืมแล้ว) {
			
		}else {
			Customers customer = customerService.getById(customeridI);
			User user = controllerHelper.getAuthenticatedUser(request);
			rentalService.createRental(customer,book,user);
			Cookie bookidcookie = new Cookie("bookIdCook", bookid);
			bookidcookie.setValue(null);
			response.addCookie(bookidcookie);
			}
		return "/rental_completed";
	}
			
	@GetMapping("/rental_completed")
	public String rental_completed() {	
		return "/rental_completed";
	}
	
	@GetMapping("/my_books")
	public String listFirstPage(Model model) {
		return listByPage(1,model,"firstname","asc", null);
	}
	
	@GetMapping("/my_books/page/{pageNum}")
	public String listByPage(@PathVariable(name = "pageNum")int pageNum,Model model, 
			@Param("sortField")String sortField, 
			@Param("sortDir")String sortDir,
			@Param("keyword")String keyword){

		Page<Customers> page = customerService.listByPage(pageNum, sortField, sortDir, keyword);
		List<Customers> listCustomers = page.getContent();
		long startCount = (pageNum - 1) * CustomerService.CUSTOMERS_PER_PAGE + 1;
		long endCount = startCount + CustomerService.CUSTOMERS_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc"; 
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("customers", listCustomers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		return "MyBook";
	}
		
	@GetMapping("/my_books/{customerid}")
	public String getAllMybooks(Model showModel,HttpServletResponse response,@PathVariable("customerid")int customerId){
		Customers customer = customerService.getById(customerId);
		List<Rental> rentals = rentalService.listRental(customer);
		List<Book> booklist = new ArrayList<>();

		for(Rental rental : rentals) {
			Book book = rental.getBook();
			booklist.add(book);

		}
		showModel.addAttribute("customers",customer);
		showModel.addAttribute("book",booklist);
		showModel.addAttribute("rental",rentals);
		
		return "/MyBook2";
	}
}
