package com.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class BookController {
	@Autowired
	private BookRepository bookrepository;

	@GetMapping("/books")
	public List<Book> getAllBook() {
		return bookrepository.findAll();
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Long userId) throws Exception {

		Book book = bookrepository.findById(userId).orElseThrow(() -> new Exception("user not found" + userId));

		return ResponseEntity.ok().body(book);

	}

	@PostMapping("/book")
	public Book createBook(@Valid @RequestBody Book book) {
		return bookrepository.save(book);
	}

	@PutMapping("/book/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Long bookId, @Valid @RequestBody Book bookDetail)
			throws Exception {

		Book book = bookrepository.findById(bookId).orElseThrow(() -> new Exception("book not found" + bookId));

		book.setName(bookDetail.getName());
		book.setAuthor(bookDetail.getAuthor());

		final Book bookUpdate = bookrepository.save(book);
		return ResponseEntity.ok().body(bookUpdate);

	}

	@DeleteMapping("/book/{id}")
	public void deleteBook(@PathVariable(value = "id") Long bookId) throws Exception {

		Book book = bookrepository.findById(bookId).orElseThrow(() -> new Exception("book not found" + bookId));

		bookrepository.delete(book);
	}

}
