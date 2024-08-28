package com.iseem.backend.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTask {
    @EmbeddedId
    private UserTaskId id;
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName="id",insertable = false,updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id",referencedColumnName="id",insertable = false,updatable = false)
    private Task task;

    private Boolean isViewed = false;
    private LocalDateTime completeDate ;

}
