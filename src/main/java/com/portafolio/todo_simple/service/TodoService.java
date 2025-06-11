package com.portafolio.todo_simple.service;

import com.portafolio.todo_simple.model.Todo;

public interface TodoService {

    Todo getTodoById(int id);

    Todo createTodo(Todo newTodo);

    Todo updateTodo(int id, Todo updatedTodo);

    void deleteTodo(int id);
}
