package com.emad.simplerestapp.controller;

import com.emad.simplerestapp.controller.api.ControllersCommonMethods;
import com.emad.simplerestapp.exceptions.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.service.api.CommentService;
import com.emad.simplerestapp.service.api.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController extends ControllersCommonMethods<Comment> {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Comment>> getAllComments(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return returnResponseEntity(commentService.getAllComments(pageNo, pageSize));
    }

    @GetMapping(value = "/{postId:\\d{1,6}}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable int postId) {
        return returnResponseEntity(commentService.getCommentsByPostId(postId));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Comment> create(@RequestBody Comment comment) throws MasterEntityNotFoundException {
        Comment createdComment = commentService.create(comment);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdComment.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping(value = "/{id:\\d{1,6}}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> updateCommentById(@PathVariable int id, @RequestBody Map<Object, Object> fields) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            commentService.update(comment.get(), fields);
            return returnResponseEntity(HttpStatus.OK);
        }
        return returnResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{id:\\d{1,6}}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Void> deleteCommentById(@PathVariable int id) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            commentService.deleteByCommentId(id);
            return returnResponseEntity(HttpStatus.OK);
        }
        return returnResponseEntity(HttpStatus.NO_CONTENT);
    }

}
