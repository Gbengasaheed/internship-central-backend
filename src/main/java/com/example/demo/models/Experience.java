package com.example.demo.models;

import com.example.demo.enums.EmploymentType;
import com.example.demo.enums.Location;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "experiences")
public class Experience extends IAppendableReference {

    @Column(nullable = false, updatable = false)
    private long userId;

    private String title;

    private EmploymentType employmentType;

    private String companyName;

    private Location location;

    private String description;

    private Date startDate;

    private Date endDate;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private Date updatedAt;
}
