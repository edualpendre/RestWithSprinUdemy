package com.edualpendre.restWithSpring.repository;

import com.edualpendre.restWithSpring.data.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
