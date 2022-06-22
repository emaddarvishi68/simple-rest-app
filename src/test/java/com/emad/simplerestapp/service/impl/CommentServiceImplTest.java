package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Comment;
import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.model.User;
import com.emad.simplerestapp.repository.CommentRepository;
import com.emad.simplerestapp.repository.PostRepository;
import com.emad.simplerestapp.service.api.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class CommentServiceImplTest {

    @InjectMocks
    CommentServiceImpl commentService;
    @Mock
    CommentRepository commentRepository;
    @Mock
    PostService postService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    private List<Comment> getCommentList1() {
        Comment comment1 = Comment.builder().id(1).body("comment1").email("comment1").name("comment1").postId(1).build();
        Comment comment2 = Comment.builder().id(2).body("comment2").email("comment2").name("comment2").postId(2).build();
        return Arrays.asList(comment1, comment2);
    }

    private List<Comment> getCommentList2() {
        Comment comment1 = Comment.builder().id(1).body("comment1").email("comment1").name("comment1").postId(1).build();
        Comment comment2 = Comment.builder().id(2).body("comment2").email("comment2").name("comment2").postId(2).build();
        Comment comment3 = Comment.builder().id(3).body("comment3").email("comment3").name("comment3").postId(3).build();
        return Arrays.asList(comment1, comment2, comment3);
    }

    private Comment getComment() {
        return getCommentList1().stream().findFirst().get();
    }

    private Post getPost() {
        return Post.builder().
                body("test")
                .id(1)
                .userId(1)
                .title("test")
                .build();
    }

    @Test
    public void saveTest() {
        List<Comment> comments = getCommentList1();
        when(commentRepository.saveAll(comments)).thenReturn(comments);
        List<Comment> commentList = (List<Comment>) commentService.save(comments);
        assertFalse(commentList.isEmpty());
    }

    @Test
    public void getCommentsByPostIdTest() throws MasterEntityNotFoundException {
        Post post = getPost();
        when(postService.getPostById(post.getId())).thenReturn(Optional.of(post));
        when(commentRepository.getCommentsByPostId(post.getId())).thenReturn(getCommentList1());
        List<Comment> commentsByPostId = commentService.getCommentsByPostId(post.getId());
        assertFalse(commentsByPostId.isEmpty());
    }

    @Test
    public void getAllCommentsTest() {
        Page<Comment> commentPages1 = new PageImpl<>(getCommentList1());

        int pageNumber = 0;
        int pageSize = 2;

        when(commentRepository.findAll(PageRequest.of(pageNumber, pageSize))).thenReturn(commentPages1);
        Page<Comment> commentPages2 = commentService.getAllComments(pageNumber, pageSize);
        assertEquals(commentPages1.getSize(), commentPages2.stream().count());
    }

    @Test
    public void createTest1() throws MasterEntityNotFoundException {
        Comment comment = getComment();
        Post post = getPost();
        when(postService.getPostById(post.getId()))
                .thenReturn(Optional.of(post));
        when(commentRepository.save(comment))
                .thenReturn(comment);
        Optional<Comment> comment1 = commentService.create(comment);
        assertEquals(comment, comment1.get());
    }

    @Test
    public void createTest2() throws MasterEntityNotFoundException {
        Comment comment = getComment();
        Post post = getPost();
        when(postService.getPostById(post.getId()))
                .thenReturn(Optional.empty());
        when(commentRepository.save(comment))
                .thenReturn(comment);
        assertThrows(MasterEntityNotFoundException.class, () -> {
            commentService.create(comment);
        });
    }


    @Test
    public void deleteByCommentIdTest() {
        Comment comment = getComment();
        doNothing().when(commentRepository).deleteById(comment.getId());
        Optional<Comment> comment1 = commentService.deleteByCommentId(comment.getId());
        assertFalse(comment1.isPresent());
    }

    @Test
    public void updateTest() {
        Comment comment = getComment();
        String body = "emad";
        Map<Object, Object> fields = new HashMap<>();
        fields.put("body", body);
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        when(commentService.getCommentById(comment.getId())).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);

        Optional<Comment> updatedComment = commentService.update(comment.getId(), fields);
        assertEquals(body, updatedComment.get().getBody());
    }

}
