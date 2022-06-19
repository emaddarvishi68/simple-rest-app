package com.emad.simplerestapp.model;

import io.swagger.annotations.ApiModelProperty;
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
@Builder
@NamedQuery(name = Todo.GET_TODOS_BY_USERID_AND_COMPLETED, query = "select t from Todo t where t.userId = ?1 and t.completed = ?2")
public class Todo extends RepresentationModel<Todo> implements Serializable {

    public static final String GET_TODOS_BY_USERID_AND_COMPLETED = "todo.getTodosByUseridAndCompleted";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "id od Todo",name="id",required=true,value="1")
    private int id;
    @ManyToOne
    @JoinColumn(name = "id", updatable = false, insertable = false)
    @Transient
    private User user;
    @Column(name = "userId", nullable = false)
    @ApiModelProperty(notes = "user id of Todo",name="userId",required=true,value="1")
    private int userId;
    @Column(nullable = false)
    @ApiModelProperty(notes = "title od Todo",name="title",required=true,value="test")
    private String title;
    @Column(nullable = false)
    @ApiModelProperty(notes = "if task completed ?",name="completed",required=true,value="true")
    private boolean completed;

}
