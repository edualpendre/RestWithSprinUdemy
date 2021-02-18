package com.edualpendre.restWithSpring.controller;

import com.edualpendre.restWithSpring.data.vo.v1.PersonVO;
import com.edualpendre.restWithSpring.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

//@CrossOrigin(origins = "http://localhost:8080")
@Tag(name = "Person Endpoint")
@RestController
@RequestMapping("/api/person/v1")
public class PersonController {
	
	@Autowired
	private PersonServices service;

	@CrossOrigin(origins = "http://localhost:8080")
	@Operation(summary = "Find all people" )
	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity findAll(
			@PageableDefault(sort = "firstName", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable page) {
		Page<PersonVO> persons = service.findAll(page);
		persons.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

		return ResponseEntity.ok(persons);
	}

//	@CrossOrigin(origins = "http://localhost:8080")
//	@Operation(summary = "Find all people" )
//	@GetMapping(produces = {"application/json", "application/xml", "application/x-yaml" })
//	public ResponseEntity<CollectionModel<PersonVO>> findAll(
//			@RequestParam(value = "page", defaultValue = "0") int page,
//			@RequestParam(value = "limit", defaultValue = "12") int limit,
//			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
//
//		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
//
//		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
//
//		Page<PersonVO> persons =  service.findAll(pageable);
//		persons.stream()
//				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
//
//		Link findAllLink = linkTo(methodOn(PersonController.class).findAll(page, limit, direction)).withSelfRel();
//
//		return ResponseEntity.ok(CollectionModel.of(persons, findAllLink));
//	}

    @Operation(summary = "Find a specific person by name" )
    @GetMapping(value = "/findPersonByName/{firstName}", produces = { "application/json", "application/xml", "application/x-yaml" })
    public ResponseEntity findPersonByName(
    		@PathVariable("firstName") String name,
			@PageableDefault(sort = "firstName", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable page) {

        Page<PersonVO> persons =  service.findPersonByName(name, page);
        persons.stream().forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));

        return ResponseEntity.ok(persons);
    }

//	@Operation(summary = "Find a specific person by name" )
//	@GetMapping(value = "/findPersonByName/{firstName}", produces = { "application/json", "application/xml", "application/x-yaml" })
//	public ResponseEntity<CollectionModel<PersonVO>> findPersonByName(
//			@PathVariable("firstName") String firstName,
//			@RequestParam(value="page", defaultValue = "0") int page,
//			@RequestParam(value="limit", defaultValue = "12") int limit,
//			@RequestParam(value="direction", defaultValue = "asc") String direction) {
//
//		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
//
//		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
//
//		Page<PersonVO> persons =  service.findPersonByName(firstName, pageable);
//		persons
//				.stream()
//				.forEach(p -> p.add(
//						linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()
//						)
//				);
//
//		Link findAllLink = linkTo(methodOn(PersonController.class).findAll(page, limit, direction)).withSelfRel();
//
//		return ResponseEntity.ok(CollectionModel.of(persons, findAllLink));
//	}

    @CrossOrigin(origins = {"http://localhost:8080", "https://developer.mozilla.org/pt-BR/docs/Web/HTTP/Controle_Acesso_CORS/"})
	@Operation(summary = "Find a specific person by your ID" )
	@GetMapping(path = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
	public PersonVO findById(@PathVariable("id") Long id) {
		PersonVO personVO = service.findById(id);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}

	@Operation(summary = "Create a new person")
	@PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
			consumes = {"application/json", "application/xml", "application/x-yaml"})
	public PersonVO create(@RequestBody PersonVO person) {
		PersonVO personVO =  service.create(person);
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		return personVO;
	}

	@Operation(summary = "Update a specific person")
	@PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
			consumes = {"application/json", "application/xml", "application/x-yaml"})
	public PersonVO update(@RequestBody PersonVO person) {
		PersonVO personVO =  service.update(person);
		personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
		return personVO;
	}

    @Operation(summary = "Disable a specific person by your ID" )
    @PatchMapping(path = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public PersonVO disablePerson(@PathVariable("id") Long id) {
        PersonVO personVO = service.disablePerson(id);
        personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return personVO;
    }

    @Operation(summary = "Delete a specific person by your ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}	
	
}