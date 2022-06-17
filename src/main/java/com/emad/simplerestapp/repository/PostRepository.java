package com.emad.simplerestapp.repository;

import com.emad.simplerestapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> getAllPostsByTitle(String title);

}
