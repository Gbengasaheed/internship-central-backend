package com.example.demo.dtos.projections;

import java.util.Date;

public interface ApplicationProjection {

    Long getUserId();

    String getCvUrl();

    Date getCreatedAt();
}
