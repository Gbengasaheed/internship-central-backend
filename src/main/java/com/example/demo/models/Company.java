package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "companies")
public class Company extends IAppendableReference{

    @Column(nullable = false, length = 50)
    private String name;

    private Integer size;

    @Column(nullable = false, length = 20)
    private String industry;

    @Column(nullable = false, length = 20)
    private String location;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}
