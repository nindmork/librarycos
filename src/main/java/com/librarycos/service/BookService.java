package com.librarycos.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.librarycos.entity.Book;
import com.librarycos.entity.BookAndPrice;
import com.librarycos.entity.BookStatus;
import com.librarycos.entity.User;
import com.librarycos.repository.BookRepository;


@Service
public class BookService {
	public static final int BOOK_PER_PAGE = 10;
	@Autowired
	private BookRepository bRepo;
	
	private List<BookAndPrice> bookAndPriceList;

	public void save(Book b,User user) {
		b.setStatus(BookStatus.พร้อมยืม);
		b.setUser(user);
		bRepo.save(b);
	}
	
	public List<Book> getAllBook(){
		return bRepo.findAll();
	}
	
	public Book getBookById(int id) {
		return bRepo.findById(id).get();
	}
	
	public void deleteById(int id) {
		bRepo.deleteById(id);
	}
	
	public Page<Book> listByPage(int pageNum, String sortField, String sortDir, String keyword ){
		Sort sort = Sort.by(sortField);
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, BOOK_PER_PAGE,sort);
		if (keyword != null) {
			return bRepo.findAll(keyword, pageable);
		}
		return bRepo.findAll(pageable);
	}
	public List<BookAndPrice> getAllBookAndPrice() {
        return bookAndPriceList;
    }
}
