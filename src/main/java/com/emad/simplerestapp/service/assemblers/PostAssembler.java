package com.emad.simplerestapp.service.assemblers;

import com.emad.simplerestapp.controller.PostController;
import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.model.dto.PostDto;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * post mapper for using in hateoas
 */
@Component
public class PostAssembler extends RepresentationModelAssemblerSupport<Post, PostDto> {

    public PostAssembler() {
        super(PostController.class, PostDto.class);
    }

    @Override
    public PostDto toModel(Post entity) {
        PostDto post = instantiateModel(entity);
        post.add(
                linkTo(methodOn(PostController.class)
                        .getPostById(entity.getId()))
                        .withSelfRel());
        post.setId(entity.getId());
        post.setBody(entity.getBody());
        post.setTitle(entity.getTitle());

        return post;
    }

}
