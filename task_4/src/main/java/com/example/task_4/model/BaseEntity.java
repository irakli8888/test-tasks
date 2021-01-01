package com.example.task_4.model;


import lombok.Data;


import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@MappedSuperclass
@Data
public class BaseEntity {
    private ZonedDateTime postTime;
    private ZonedDateTime putTime;
    private Double version=0.00;

    public BaseEntity(ZonedDateTime putTime) {
        this.version=0.00;
        this.postTime=ZonedDateTime.now();
        this.putTime=putTime;
    }
    public BaseEntity() {
        this.version=0.00;
        this.postTime=ZonedDateTime.now();
    }
}
