package com.emad.simplerestapp.model.dto;

import com.emad.simplerestapp.model.Todo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@JsonRootName(value = "todo")
@Relation(collectionRelation = "todos")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoDto  extends RepresentationModel<Todo> {

    private int id;
    private int userId;
    private String title;
    private boolean completed;

}
