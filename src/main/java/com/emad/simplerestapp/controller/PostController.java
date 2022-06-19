package com.emad.simplerestapp.controller;

import com.emad.simplerestapp.controller.api.ControllersCommonMethods;
import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.model.dto.CommentDto;
import com.emad.simplerestapp.model.dto.PostDto;
import com.emad.simplerestapp.service.api.CommentService;
import com.emad.simplerestapp.service.api.PostService;
import com.emad.simplerestapp.service.assemblers.CommentAssembler;
import com.emad.simplerestapp.service.assemblers.PostAssembler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * post controller
 */
@Api(value = "Post")
@RestController
@RequestMapping("/posts")
public class PostController extends ControllersCommonMethods<Post> {
    private final PostService postService;
    private final CommentService commentService;
    private final PagedResourcesAssembler<Post> pagedResourcesAssembler;
    private final PostAssembler postAssembler;
    private final CommentAssembler commentAssembler;

    public PostController(PostService postService, CommentService commentService, PostAssembler postAssembler
            , PagedResourcesAssembler<Post> pagedResourcesAssembler, CommentAssembler commentAssembler) {
        this.postService = postService;
        this.commentService = commentService;
        this.postAssembler = postAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.commentAssembler = commentAssembler;
    }

    @ApiOperation(value = "Get all posts(with pagination)", response = ResponseEntity.class)
    @GetMapping(params = {"pageNumber", "pageSize"}, consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<PagedModel<PostDto>> getAllPosts(@RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        Page<Post> allPosts = postService.getAllPosts(pageNumber, pageSize);
        PagedModel<PostDto> postDtos = pagedResourcesAssembler.toModel(allPosts, postAssembler);
        return allPosts.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Get post by post id", response = ResponseEntity.class)
    @GetMapping(value = "/{id:\\d{1,6}}", consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<PostDto> getPostById(@PathVariable int id) {
        return postService.getPostById(id)
                .map(postAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @ApiOperation(value = "Get comments of specific post by post id", response = ResponseEntity.class)
    @GetMapping(value = "/{id:\\d{1,6}}/comments", consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<CommentDto>> getCommentsByPostId(@PathVariable int id) throws MasterEntityNotFoundException {
        List<Comment> commentsList = commentService.getCommentsByPostId(id);
        return commentsList.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(commentAssembler.toCollectionModel(commentsList), HttpStatus.OK);

    }

    @ApiOperation(value = "Get all posts that have the specific keyword in their title", response = ResponseEntity.class)
    @GetMapping(params = "title", consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<PostDto>> getAllPostsByTitle(@RequestParam(defaultValue = "") String title) {
        List<Post> allPostsByTitle = postService.getAllPostsByTitle(title);
        return allPostsByTitle.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(postAssembler.toCollectionModel(allPostsByTitle), HttpStatus.OK);
    }

    @ApiOperation(value = "Create a post", response = ResponseEntity.class)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> create(@RequestBody Post post) {
        Optional<Post> createdPost = postService.create(post);
        if (createdPost.isPresent()) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdPost.get().getId()).toUri();
            return ResponseEntity.created(location).build();
        }
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Update a post by post id", response = ResponseEntity.class)
    @PatchMapping(value = "/{id:\\d{1,6}}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Optional<Post>> updatePostById(@PathVariable int id, @RequestBody Map<Object, Object> fields) {
        return returnResponseEntityForDeleteOrUpdate(postService.update(id, fields));
    }

    @ApiOperation(value = "Delete a post by post id", response = ResponseEntity.class)
    @DeleteMapping(value = "/{id:\\d{1,6}}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Optional<Post>> deletePostById(@PathVariable int id) {
        return returnResponseEntityForDeleteOrUpdate(postService.deleteByPostId(id));
    }

}
