package com.edualpendre.restWithSpring.repository;


import com.edualpendre.restWithSpring.data.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}