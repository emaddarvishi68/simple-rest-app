package com.emad.simplerestapp.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.COMMENT)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
@NamedQuery(name = Comment.GET_COMMENTS_BY_POST_ID, query = "select c from Comment c where c.postId = ?1 ")
public class Comment extends RepresentationModel<Comment> implements Serializable {

    public static final String GET_COMMENTS_BY_POST_ID = "Comment.getCommentsByPostId";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "id of Comment", name = "id", required = true, value = "1")
    private int id;
    @ManyToOne
    @JoinColumn(name = "id")
    @Transient
    private Post post;
    @Column(name = "postId", nullable = false)
    @ApiModelProperty(notes = "id of Post",name="postId",required=true,value="12")
    private int postId;
    @Column(nullable = false)
    @ApiModelProperty(notes = "name of Comment",name="name",required=true,value="test")
    private String name;
    @Column(nullable = false, length = 63)
    @ApiModelProperty(notes = "email of user",name="email",required=true,value="darvishi.emad@gmail.com")
    private String email;
    @Column(nullable = false, length = 511)
    @ApiModelProperty(notes = "body of Comment",name="body",required=true,value="test 1")
    private String body;

}
