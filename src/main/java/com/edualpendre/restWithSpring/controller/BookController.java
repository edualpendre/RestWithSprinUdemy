package com.edualpendre.restWithSpring.controller;

import com.edualpendre.restWithSpring.data.vo.v1.BookVO;
import com.edualpendre.restWithSpring.services.BookServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<CollectionModel<BookVO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "author"));

		Page<BookVO> books =  service.findAll(pageable);
		books.stream()
				.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));

		Link findAllLink = linkTo(methodOn(BookController.class).findAll(page, limit, direction)).withSelfRel();

		return ResponseEntity.ok(CollectionModel.of(books, findAllLink));

	}

	@Operation(summary = "Find a specific book by title" )
	@GetMapping(value = "/findBookByTitle/{title}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<CollectionModel<BookVO>> findBookByTitle(
			@PathVariable("title") String title,
			@RequestParam(value="page", defaultValue = "0") int page,
			@RequestParam(value="limit", defaultValue = "12") int limit,
			@RequestParam(value="direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "title"));

		Page<BookVO> books =  service.findBookByTitle(title, pageable);
		books.stream()
				.forEach(p -> p.add(
						linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()
						)
				);

		return ResponseEntity.ok(CollectionModel.of(books));
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