package com.emad.simplerestapp.service.assemblers;

import com.emad.simplerestapp.controller.TodoController;
import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Todo;
import com.emad.simplerestapp.model.dto.TodoDto;
import lombok.SneakyThrows;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * todo mapper for using in hateoas
 */
@Component
public class TodoAssembler extends RepresentationModelAssemblerSupport<Todo, TodoDto> {

    public TodoAssembler() {
        super(TodoController.class, TodoDto.class);
    }

    @SneakyThrows
    @Override
    public TodoDto toModel(Todo entity) {
        TodoDto todoDto = instantiateModel(entity);
        try {
            todoDto.add(
                    linkTo(methodOn(TodoController.class)
                            .getTodosByUserIdAndCompleted(entity.getUserId(), entity.isCompleted()))
                            .withSelfRel());
        } catch (MasterEntityNotFoundException e) {
            throw new MasterEntityNotFoundException("There is no user with id: "+entity.getUserId());
        }
        todoDto.setId(entity.getId());
        todoDto.setUserId(entity.getUserId());
        todoDto.setTitle(entity.getTitle());
        todoDto.setCompleted(entity.isCompleted());

        return todoDto;
    }

    @Override
    public CollectionModel<TodoDto> toCollectionModel(Iterable<? extends Todo> entities) {
        CollectionModel<TodoDto> todoDtos = super.toCollectionModel(entities);
        todoDtos.add(linkTo(methodOn(TodoController.class).getAllTodos()).withRel("allTodos"));
        return todoDtos;
    }
}
