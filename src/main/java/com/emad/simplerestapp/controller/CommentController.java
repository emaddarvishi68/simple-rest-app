package com.emad.simplerestapp.controller;

import com.emad.simplerestapp.controller.api.ControllersCommonMethods;
import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.model.dto.CommentDto;
import com.emad.simplerestapp.service.api.CommentService;
import com.emad.simplerestapp.service.assemblers.CommentAssembler;
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
 * comment controller
 */
@Api(value = "Comment")
@RestController
@RequestMapping("/comments")
public class CommentController extends ControllersCommonMethods<Comment> {
    private final CommentService commentService;
    private final PagedResourcesAssembler<Comment> pagedResourcesAssembler;
    private final CommentAssembler commentAssembler;

    public CommentController(CommentService commentService, PagedResourcesAssembler<Comment> pagedResourcesAssembler, CommentAssembler commentAssembler) {
        this.commentService = commentService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.commentAssembler = commentAssembler;
    }

    @ApiOperation(value = "Get all comments(with pagination)", response = ResponseEntity.class)
    @GetMapping(params = {"pageNumber", "pageSize"}, consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<PagedModel<CommentDto>> getAllComments(@RequestParam(defaultValue = "0") Integer pageNumber, @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<Comment> allComments = commentService.getAllComments(pageNumber, pageSize);
        PagedModel<CommentDto> commentDtos = pagedResourcesAssembler.toModel(allComments, commentAssembler);
        return allComments.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Get single comment by id", response = ResponseEntity.class)
    @GetMapping(value = "/single/{id:\\d{1,6}}", consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable int id) {
        return commentService.getCommentById(id)
                .map(commentAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @ApiOperation(value = "Get comments of specific post by post id", response = ResponseEntity.class)
    @GetMapping(value = "/{postId:\\d{1,6}}", consumes = "application/json", produces = "application/hal+json")
    public ResponseEntity<CollectionModel<CommentDto>> getCommentsByPostId(@PathVariable int postId) throws MasterEntityNotFoundException {
        List<Comment> commentList = commentService.getCommentsByPostId(postId);
        CollectionModel<CommentDto> commentDtos = commentAssembler.toCollectionModel(commentList);
        return commentList.isEmpty()
                ? new ResponseEntity<>(commentDtos, HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @ApiOperation(value = "Create a comment", response = ResponseEntity.class)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> create(@RequestBody Comment comment) throws MasterEntityNotFoundException {
        Optional<Comment> createdComment = commentService.create(comment);
        if (createdComment.isPresent()) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdComment.get().getId()).toUri();
            return ResponseEntity.created(location).build();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Update a comment by comments id", response = ResponseEntity.class)
    @PatchMapping(value = "/{id:\\d{1,6}}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Optional<Comment>> updateCommentById(@PathVariable int id, @RequestBody Map<Object, Object> fields) {
        return returnResponseEntityForDeleteOrUpdate(commentService.update(id, fields));
    }

    @ApiOperation(value = "Delete a comment by comments id", response = ResponseEntity.class)
    @DeleteMapping(value = "/{id:\\d{1,6}}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Optional<Comment>> deleteCommentById(@PathVariable int id) {
        return returnResponseEntityForDeleteOrUpdate(commentService.deleteByCommentId(id));
    }

}
