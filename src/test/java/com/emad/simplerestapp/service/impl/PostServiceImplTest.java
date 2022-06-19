package com.emad.simplerestapp.service.impl;

import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Post;
import com.emad.simplerestapp.repository.PostRepository;
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
    ObjectMapper objectMapper;

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

    private Post getPost() {
        return getPostList2().stream().findFirst().get();
    }

    @Test
    public void saveTest() {
        init();
        List<Post> posts = getPostList1();
        Iterable<Post> save = postService.save(posts);
        verify(postRepository, times(1)).saveAll(save);
    }

    @Test
    public void getAllPostsTest() {
        Page<Post> postPages1 = new PageImpl<>(getPostList1());

        int pageNumber = 0;
        int pageSize = 2;

        when(postRepository.findAll(PageRequest.of(pageNumber, pageSize))).thenReturn(postPages1);
        Page<Post> postPages2 = postService.getAllPosts(pageNumber, pageSize);
        assertEquals(2, postPages2.stream().count());
    }

    @Test
    public void createTest1() {
        Post post = getPostList1().get(1);
        when(postRepository.save(post)).thenReturn(post);
        Optional<Post> savedPost = postService.create(post);
        assertTrue(savedPost.isPresent());
    }

    @Test
    public void createTest2() {
        Post post = Post.builder().userId(100).title("test").body("test").id(4).build();
        when(postRepository.save(post)).thenReturn(post);
        assertThrows(MasterEntityNotFoundException.class, () -> {
            postService.create(post);
        });
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void getAllPostsByTitleTest() {
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
    public void updateTest1() {
        Post post = Post.builder().id(100).body("100").title("100").userId(100).build();
        Map<Object, Object> fields = new HashMap<>();
        fields.put("id", 200);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(postService.getPostById(post.getId())).thenReturn(Optional.of(post));
        assertThrows(MasterEntityNotFoundException.class, () -> {
            postService.update(post.getId(), fields);
        });
        verify(postRepository, times(1)).save(post);

    }

    @Test
    public void updateTest2() {
        Post post = getPost();
        String title = "emad";
        Map<Object, Object> fields = new HashMap<>();
        fields.put("title", title);
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(postService.getPostById(post.getId())).thenReturn(Optional.of(post));

        Optional<Post> update = postService.update(post.getId(), fields);
        Post saved = verify(postRepository, times(1)).save(post);
        assertEquals(title, saved.getTitle());

    }

}
