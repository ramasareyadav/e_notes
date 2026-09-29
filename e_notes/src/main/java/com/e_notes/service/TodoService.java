package com.e_notes.service;

import com.e_notes.dto.TodoDto;

import java.util.List;

public interface TodoService {

    public Boolean saveTodo(TodoDto todoDto);
    public TodoDto getTodoById(Integer id) throws Exception;
    public List<TodoDto> getTodoByUser();
}
