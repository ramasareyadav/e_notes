package com.e_notes.controller;

import com.e_notes.dto.TodoDto;
import com.e_notes.service.TodoService;
import com.e_notes.util.CommonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Save Todo
    @PostMapping("/save")
    public ResponseEntity<?> saveTodo(@RequestBody TodoDto todoDto) {

        Boolean result = todoService.saveTodo(todoDto);

        if (result) {
            return CommonUtil.createBuildResponseMessage(
                    "Todo saved successfully",
                    HttpStatus.CREATED
            );
        }

        return CommonUtil.createErrorResponseMessage(
                "Todo not saved",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    // Get Todo by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable Integer id) throws Exception {

        TodoDto todoDto = todoService.getTodoById(id);

        return CommonUtil.createBuildResponse(
                todoDto,
                HttpStatus.OK
        );
    }

    // Get all Todos of user
    @GetMapping("/user")
    public ResponseEntity<?> getTodoByUser() {

        List<TodoDto> todoList = todoService.getTodoByUser();

        return CommonUtil.createBuildResponse(
                todoList,
                HttpStatus.OK
        );
    }
}