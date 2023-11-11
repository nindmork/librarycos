package com.librarycos.entity;

public class BookAndPrice {

	private String bookName;
    private int price;

    public BookAndPrice() {
    }

    public BookAndPrice(String bookName) {
        this.bookName = bookName;
        
    }
    public BookAndPrice(String bookName, int price) {
        this.bookName = bookName;
        this.price = price;
    }
   
    public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}


}
