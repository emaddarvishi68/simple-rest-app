package com.emad.simplerestapp.service.api;

import com.emad.simplerestapp.exception.MasterEntityNotFoundException;
import com.emad.simplerestapp.model.Comment;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CommentService {

    /**
     * get Comment by Id
     *
     * @param id
     * @return Optional<Comment>
     */
    Optional<Comment> getById(int id);

    /**
     * save List of Comment
     *
     * @param commentList
     * @return Iterable<Comment>
     */
    Iterable<Comment> save(List<Comment> commentList);

    /**
     * get comments by Post id
     *
     * @param postId
     * @return List<Comment>
     * @throws MasterEntityNotFoundException
     */
    List<Comment> getCommentsByPostId(int postId) throws MasterEntityNotFoundException;

    /**
     * get all comments
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Comment> getAllComments(Integer pageNumber, Integer pageSize);

    /**
     * create a Comment
     *
     * @param comment
     * @return Optional<Comment>
     * @throws MasterEntityNotFoundException
     */
    Optional<Comment> create(Comment comment) throws MasterEntityNotFoundException;

    /**
     * delete a comment by id
     *
     * @param id
     * @return Optional<Comment>
     */
    Optional<Comment> deleteByCommentId(int id);

    /**
     * get a Comment by id
     *
     * @param id
     * @return Optional<Comment>
     */
    Optional<Comment> getCommentById(int id);

    /**
     * update a Comment
     *
     * @param commentId
     * @param fields
     * @return Optional<Comment>
     */
    Optional<Comment> update(int commentId, Map<Object, Object> fields);

    /**
     * fetch data from resource
     *
     * @param resource
     * @return List<Commen>
     * @throws IOException
     */
    List<Comment> fetchFromResource(String resource) throws IOException;

}
