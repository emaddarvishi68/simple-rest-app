package com.emad.simplerestapp.controller;

import com.emad.simplerestapp.controller.api.ControllersCommonMethods;
import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.service.api.CommentService;
import com.emad.simplerestapp.service.api.PostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController extends ControllersCommonMethods<Post> {
    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Post>> getAllPosts(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return returnResponseEntity(postService.getAllPosts(pageNo, pageSize));
    }

    @GetMapping(value = "/{id:\\d{1,6}}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Optional<Post>> getPostById(@PathVariable int id) {
        return returnResponseEntity(postService.getPostById(id));
    }

    @GetMapping(value = "/{id:\\d{1,6}}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable int id) {
        List<Comment> commentsList = commentService.getCommentsByPostId(id);
        return !commentsList.isEmpty() ? new ResponseEntity<>(commentsList, new HttpHeaders(), HttpStatus.OK) : new ResponseEntity<>(commentsList, new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    @GetMapping(params = "title", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Post>> getAllPostsByTitle(@RequestParam(defaultValue = "") String title) {
        return returnResponseEntity(postService.getAllPostsByTitle(title));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Post> create(@RequestBody Post post) {
        Post createdPost = postService.create(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping(value = "/{id:\\d{1,6}}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Optional<Post>> updatePostById(@PathVariable int id, @RequestBody Map<Object, Object> fields) {
        return returnResponseEntity(postService.update(id,fields));
    }

    @DeleteMapping(value = "/{id:\\d{1,6}}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Optional<Post>> deletePostById(@PathVariable int id) {
        return returnResponseEntity(postService.deleteByPostId(id));
    }

}
