package com.emad.simplerestapp.model.dto;

import com.emad.simplerestapp.model.Post;
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
@JsonRootName(value = "post")
@Relation(collectionRelation = "posts")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto extends RepresentationModel<Post> {

    private int id;
    private Integer userId;
    private String title;
    private String body;

}
