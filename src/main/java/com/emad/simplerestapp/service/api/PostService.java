package com.emad.simplerestapp.service.api;

import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Post;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostService {

    /**
     * save list of Post
     *
     * @param postList
     * @return Iterable<Post>
     */
    Iterable<Post> save(List<Post> postList);

    /**
     * get all posts
     *
     * @param pageNumber
     * @param pageSize
     * @return Page<Post>
     */
    Page<Post> getAllPosts(Integer pageNumber, Integer pageSize);

    /**
     * get post by id
     *
     * @param id
     * @return Optional<Post>
     */
    Optional<Post> getPostById(int id);

    /**
     * get all posts by specific title
     *
     * @param title
     * @return List<Post>
     */
    List<Post> getAllPostsByTitle(String title);

    /**
     * create a Post
     *
     * @param post
     * @return Optional<Post>
     */

    Optional<Post> create(Post post) throws MasterEntityNotFoundException;

    /**
     * delete a Post by id
     *
     * @param id
     * @return Optional<Post>
     */
    Optional<Post> deleteByPostId(int id);

    /**
     * update a Post
     *
     * @param id
     * @param fields
     * @return Optional<Post>
     */
    Optional<Post> update(int id, Map<Object, Object> fields);

    /**
     * fetch posts from a resource
     *
     * @param resource
     * @return List<Post>
     * @throws IOException
     */
    List<Post> fetchFromResource(String resource) throws IOException;

}
