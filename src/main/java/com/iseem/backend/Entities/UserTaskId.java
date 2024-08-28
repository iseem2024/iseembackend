package com.iseem.backend.Entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTaskId implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "task_id")
    private int taskId;

    
}
