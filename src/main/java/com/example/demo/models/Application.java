package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "applications")
public class Application extends IAppendableReference{

    @Column(nullable = false)
    private String cvUrl;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private long jobId;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}
