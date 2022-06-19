package com.emad.simplerestapp.model.dto;

import com.emad.simplerestapp.model.Comment;
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
@JsonRootName(value = "comment")
@Relation(collectionRelation = "comments")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto extends RepresentationModel<Comment> {

    private int id;
    private int postId;
    private String name;
    private String email;
    private String body;

}
