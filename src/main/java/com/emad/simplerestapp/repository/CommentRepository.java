package com.emad.simplerestapp.repository;

import com.emad.simplerestapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> getCommentsByPostId(int id);

}
