package com.project.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		System.out.println("Inside BookStoreApplication main");
		SpringApplication.run(BookstoreApplication.class, args);
	}

}
