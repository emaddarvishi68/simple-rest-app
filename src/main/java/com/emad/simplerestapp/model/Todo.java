package com.emad.simplerestapp.model;

import com.emad.simplerestapp.model.helper.TableName;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.TODO)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Todo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne
    private User user;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private boolean completed;

}
