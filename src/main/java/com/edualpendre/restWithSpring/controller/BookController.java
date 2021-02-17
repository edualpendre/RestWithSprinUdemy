package com.edualpendre.restWithSpring.controller;

import com.edualpendre.restWithSpring.data.vo.v1.BookVO;
import com.edualpendre.restWithSpring.services.BookServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Book Endpoint")
@RestController
@RequestMapping("/api/book/v1")
public class BookController {
	
	@Autowired
	private BookServices service;

	@Operation(summary = "Find a specific book by name" )
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml" })
	public List<BookVO> findAll() {
		List<BookVO> books =  service.findAll();
		books.stream()
				.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		return books;
	}

	@Operation(summary = "Find a specific book by your ID" )
	@GetMapping(path = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
	public BookVO findById(@PathVariable("id") Long id) {
		BookVO bookVO = service.findById(id);
		bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return bookVO;
	}

	@Operation(summary = "Create a new book")
	@PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
			consumes = {"application/json", "application/xml", "application/x-yaml"})
	public BookVO create(@RequestBody BookVO book) {
		BookVO bookVO =  service.create(book);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@Operation(summary = "Update a specific book")
	@PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
			consumes = {"application/json", "application/xml", "application/x-yaml"})
	public BookVO update(@RequestBody BookVO book) {
		BookVO bookVO =  service.update(book);
		bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
		return bookVO;
	}

	@Operation(summary = "Delete a specific book by your ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}	
	
}