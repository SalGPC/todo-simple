package com.portafolio.todo_simple.repository;

import com.portafolio.todo_simple.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TodoRepository extends JpaRepository<Todo, Integer> {

    List<Todo> findByTitleAndCompletedTrue(String title);

}
