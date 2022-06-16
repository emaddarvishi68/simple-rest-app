package com.emad.simplerestapp.model;

import com.emad.simplerestapp.model.helper.TableName;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.POST)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne
    private User user;
    @Column(nullable = true)
    private String title;
    @Column(nullable = false)
    private String body;

}
