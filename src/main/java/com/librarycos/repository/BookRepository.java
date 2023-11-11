package com.librarycos.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import com.librarycos.entity.Book;

public interface BookRepository extends JpaRepository<Book,Integer>  {

	@Query(value = "SELECT * FROM book WHERE id = ?1", nativeQuery = true)
	public List<Book> getBookID(int id);
	
	@Query("SELECT u FROM Book u WHERE CONCAT (u.id,' ',u.isbn,' ',u.name,' ',u.author) LIKE %?1%")
	public Page<Book> findAll(String keyword, Pageable pageable);
	
	@Query(value = "SELECT b.name AS name, COUNT(*) AS count FROM book b JOIN users u ON u.id = b.users_id JOIN roles r ON u.roles_id = r.id WHERE r.name = 'Staff' GROUP BY b.name", nativeQuery = true)
	public List<BookCount> findBookCountByStaff(); 
	
	public interface BookCount { 
	    String getName();
	    Long getCount();
	}
	
	@Query(value = "select cus.firstname , b.name "
			+ "from book b "
			+ "join record re on re.book_id = b.id "
			+ "join customers cus on re.customer_id = cus.id "
			+ "where re.status IN ('กำลังยืม' , 'เกินกำหนด')", nativeQuery = true)
	public List<BookCus> findBookAndCustomerWithStatus(); //ข้อ2
	
	public interface BookCus { //ข้อ2
		String getName();
		String getFirstname();
	}

	//ข้อ1
		@Query(value = "SELECT b.name AS bookName ,SUM(DATEDIFF(CURRENT_DATE, r.renta_endtime) * 10) AS price FROM record r JOIN book b ON (r.book_id = b.id) WHERE CURRENT_DATE > r.renta_endtime GROUP BY b.name ", nativeQuery = true)
		public List<BookAndPrice2> findBookWithPrice();
		public interface BookAndPrice2 { 
		    String getBookName();
		    Long getPrice();
		}
	
}
