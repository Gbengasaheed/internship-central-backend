package com.example.demo.models;

import com.example.demo.enums.Degree;
import com.example.demo.enums.FieldOfStudy;
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
@Table(name = "educations")
public class Education extends IAppendableReference {

    private String school;

    private Degree degree;

    @Column(nullable = false, updatable = false)
    private long userId;

    private FieldOfStudy fieldOfStudy;

    private Date startDate;

    private Date endDate;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}
