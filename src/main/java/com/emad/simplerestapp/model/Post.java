package com.emad.simplerestapp.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.POST)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@NamedQuery(name = Post.GET_ALL_POSTS_BY_TITLE,
        query = "select p from Post p where p.title like concat('%',?1,'%') ")
public class Post extends RepresentationModel<Post> implements Serializable {

    public static final String GET_ALL_POSTS_BY_TITLE = "Post.getAllPostsByTitle";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "id of Post",name="id",required=true,value="1")
    private int id;
    @ManyToOne
    @JoinColumn(name = "id", updatable = false, insertable = false)
    @Transient
    private User user;
    @Column(name = "userId", nullable = false)
    @ApiModelProperty(notes = "user id of Post",name="userId",required=true,value="1")
    private Integer userId;
    @Column(nullable = false)
    @ApiModelProperty(notes = "title of Post",name="title",required=true,value="test 1")
    private String title;
    @Column(nullable = false)
    @ApiModelProperty(notes = "body of Post",name="body",required=true,value="test 2")
    private String body;

}
