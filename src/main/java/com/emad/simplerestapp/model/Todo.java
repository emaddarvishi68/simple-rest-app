package com.emad.simplerestapp.model;

import com.emad.simplerestapp.staticvalues.TableName;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = TableName.TODO)
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@NamedQueries({@NamedQuery(name = Todo.GET_TODOS_BY_USERID_AND_COMPLETED, query = "select t from Todo t where t.userId = ?1 and t.completed = ?2")})
public class Todo extends RepresentationModel<Todo> implements Serializable {

    public static final String GET_TODOS_BY_USERID_AND_COMPLETED = "todo.getTodosByUseridAndCompleted";
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
