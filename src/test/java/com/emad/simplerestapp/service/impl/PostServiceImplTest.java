package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.model.User;
import com.emad.simplerestapp.repository.PostRepository;
import com.emad.simplerestapp.repository.UserRepository;
import com.emad.simplerestapp.service.api.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.Mockito.*;

public class PostServiceImplTest {

    @InjectMocks
    PostServiceImpl postService;
    @Mock
    PostRepository postRepository;
    @Mock
    UserService userService;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    private List<Post> getPostList1() {
        Post post1 = Post.builder().id(1).body("post1").title("post1").userId(1).build();
        Post post2 = Post.builder().id(2).body("post2").title("post2").userId(2).build();
        return Arrays.asList(post1, post2);
    }

    private List<Post> getPostList2() {
        Post post1 = Post.builder().id(1).body("post1").title("post1").userId(1).build();
        Post post2 = Post.builder().id(2).body("post2").title("post2").userId(2).build();
        Post post3 = Post.builder().id(3).body("post3").title("post3").userId(3).build();
        return Arrays.asList(post1, post2, post3);
    }

    private Post getPostWithUserId(int userId) {
        return Post.builder().
                body("test")
                .id(1000)
                .userId(userId)
                .title("test")
                .build();
    }

    private Post getPost() {
        return getPostList2().stream().findFirst().get();
    }

    @Test
    public void saveTest() {
        List<Post> posts = getPostList1();
        when(postRepository.saveAll(posts)).thenReturn(posts);
        List<Post> saveList = (List<Post>) postService.save(posts);
        assertFalse(saveList.isEmpty());
    }

    @Test
    public void getAllPostsTest1() {
        Page<Post> postPages1 = new PageImpl<>(getPostList1());

        int pageNumber = 0;
        int pageSize = 2;

        when(postRepository.findAll(PageRequest.of(pageNumber, pageSize))).thenReturn(postPages1);
        Page<Post> postPages2 = postService.getAllPosts(pageNumber, pageSize);
        assertEquals(postPages1.getSize(), postPages2.stream().count());
    }

    @Test
    public void getAllPostsTest2() {
        Page<Post> postPages1 = new PageImpl<>(getPostList1());
        Page<Post> postPages2 = new PageImpl<>(getPostList2());

        int pageNumber = 5;
        int pageSize = 10;

        when(postRepository.findAll(PageRequest.of(pageNumber, pageSize))).thenReturn(postPages2);
        Page<Post> postPages3 = postService.getAllPosts(pageNumber, pageSize);
        assertEquals(postPages1.getSize(), postPages3.stream().count());
    }

    @Test
    public void createTest1() throws MasterEntityNotFoundException {
        Post post = getPostWithUserId(10000);
        when(userService.getUserById(post.getUserId()))
                .thenReturn(Optional.empty());
        assertThrows(MasterEntityNotFoundException.class, () -> {
            postService.create(post);
        });
    }

    @Test
    public void createTest2() throws MasterEntityNotFoundException {
        int userId = 1000;
        Post post = getPostWithUserId(userId);
        Optional<User> user = Optional.ofNullable(User.builder().id(userId).build());
        when(userRepository.findById(userId))
                .thenReturn(user);
        when(userService.getUserById(userId))
                .thenReturn(user);
        when(postRepository.save(post))
                .thenReturn(post);
        Optional<Post> post1 = postService.create(post);
        assertEquals(post1.get(),post);
    }

    @Test
    public void getAllPostsByTitleTest1() {
        when(postRepository.getAllPostsByTitle("emad")).thenReturn(getPostList1());
        List<Post> allPostsByTitle = postService.getAllPostsByTitle("emad");
        assertEquals(allPostsByTitle.size(), getPostList1().size());
    }

    @Test
    public void getAllPostsByTitleTest2() {
        when(postRepository.getAllPostsByTitle("emad")).thenReturn(getPostList2());
        List<Post> allPostsByTitle = postService.getAllPostsByTitle("emad");
        assertEquals(allPostsByTitle.size(), getPostList1().size());
    }

    @Test
    public void getPostByIdTest1() {
        Post post = getPostList1().stream().findFirst().get();
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Optional<Post> postById = postService.getPostById(post.getId());
        assertEquals(post.getTitle(), postById.get().getTitle());
    }

    @Test
    public void getPostByIdTest2() {
        Post post = getPost();
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Optional<Post> postById = postService.getPostById(post.getId());
        assertEquals(post.getTitle(), postById.get().getTitle());
    }

    @Test
    public void deleteByPostIdTest1() {
        Post post = getPost();
        doNothing().when(postRepository).deleteById(post.getId());
        Optional<Post> post1 = postService.deleteByPostId(post.getId());
        assertFalse(post1.isPresent());
    }

    @Test
    public void updateTest() {
        Post post = getPost();
        String title = "emad";
        Map<Object, Object> fields = new HashMap<>();
        fields.put("title", title);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(postService.getPostById(post.getId())).thenReturn(Optional.of(post));
        when(postRepository.save(post)).thenReturn(post);

        Optional<Post> updatedPost = postService.update(post.getId(), fields);
        assertEquals(title, updatedPost.get().getTitle());
    }

}
