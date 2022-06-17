package com.emad.simplerestapp.model;

import com.emad.simplerestapp.staticvalues.TableName;
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
@NamedQueries({
        @NamedQuery(name = Post.GET_ALL_POSTS_BY_TITLE,
                query = "select p from Post p where p.title like concat('%',?1,'%') ")
})
public class Post extends RepresentationModel<Post> implements Serializable {

    public static final String GET_ALL_POSTS_BY_TITLE = "Post.getAllPostsByTitle";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id", updatable = false, insertable = false)
    @Transient
    private User user;

    @Column(name = "userId", nullable = false)
    private Integer userId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;

}
