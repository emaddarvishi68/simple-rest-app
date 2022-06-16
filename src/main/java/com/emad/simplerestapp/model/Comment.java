package com.emad.simplerestapp.model;

import com.emad.simplerestapp.helper.TableName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.COMMENT)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Comment extends RepresentationModel<Comment> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id")
    @Transient
    private Post post;

    @Column(name = "postId", nullable = false)
    private int postId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 63)
    private String email;
    @Column(nullable = false, length = 511)
    private String body;

}
