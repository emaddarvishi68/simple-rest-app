package com.emad.simplerestapp.service.assemblers;

import com.emad.simplerestapp.controller.CommentController;
import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.model.dto.CommentDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * comment mapper for using in hateoas
 */
@Component
public class CommentAssembler extends RepresentationModelAssemblerSupport<Comment, CommentDto> {

    public CommentAssembler() {
        super(CommentController.class, CommentDto.class);
    }

    @Override
    public CommentDto toModel(Comment entity) {
        CommentDto comment = instantiateModel(entity);
        comment.add(
                linkTo(methodOn(CommentController.class)
                        .getCommentById(entity.getId()))
                        .withSelfRel());
        comment.setId(entity.getId());
        comment.setBody(entity.getBody());
        comment.setPostId(entity.getPostId());
        comment.setName(entity.getName());

        return comment;
    }

}
