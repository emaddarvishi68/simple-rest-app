package com.emad.simplerestapp.model;

import com.emad.simplerestapp.helper.TableName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.TODO)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Todo extends RepresentationModel<Todo> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "id", updatable = false, insertable = false)
    @Transient
    private User user;

    @Column(name = "userId", nullable = false)
    private int userId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private boolean completed;

}
