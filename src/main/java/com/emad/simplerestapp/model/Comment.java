package com.emad.simplerestapp.model;

import com.emad.simplerestapp.model.helper.TableName;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.COMMENT)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne
    private Post post;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, length = 63)
    private String email;
    @Column(nullable = false, length = 511)
    private String body;

}
