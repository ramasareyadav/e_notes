package com.e_notes.service.impl;

import com.e_notes.dto.TodoDto;
import com.e_notes.exception.ResourceNotFoundException;
import com.e_notes.model.Todo;
import com.e_notes.repository.TodoRepository;
import com.e_notes.service.TodoService;
import com.e_notes.util.TodoStatus;
import com.e_notes.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    private final Validation validation;

    public TodoServiceImpl(TodoRepository todoRepository, ModelMapper modelMapper, Validation validation) {
        this.todoRepository = todoRepository;
        this.modelMapper = modelMapper;
        this.validation = validation;
    }


    @Override
    public Boolean saveTodo(TodoDto todoDto) {

        validation.todoValidation(todoDto);

        Todo todo = new Todo();

        todo.setTitle(todoDto.getTitle());

        // Only ID is stored
        todo.setStatus(todoDto.getStatus().getId());

        Todo savedTodo = todoRepository.save(todo);

        return !ObjectUtils.isEmpty(savedTodo);
    }

    @Override
    public TodoDto getTodoById(Integer id) throws Exception {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Todo Not Found! Invalid id: " + id
                        ));

        TodoDto todoDto = modelMapper.map(todo, TodoDto.class);

        setStatus(todoDto, todo);

        return todoDto;
    }

    private void setStatus(TodoDto todoDto, Todo todo) {

        for (TodoStatus st : TodoStatus.values()) {

            if (st.getId().equals(todo.getStatus())) {

                TodoDto.StatusDto statusDto = TodoDto.StatusDto.builder()
                        .id(st.getId())
                        .name(st.getName())
                        .build();

                todoDto.setStatus(statusDto);

                return;
            }
        }
    }

    @Override
    public List<TodoDto> getTodoByUser() {

        Integer userId = 2;

        List<Todo> list = todoRepository.findByCreatedBy(userId);

        return list.stream()
                .map(todo -> {

                    TodoDto todoDto = modelMapper.map(todo, TodoDto.class);

                    setStatus(todoDto, todo);

                    return todoDto;

                })
                .toList();
    }
}
