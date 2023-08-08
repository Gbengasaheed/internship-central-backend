package com.example.demo.specifications;

import com.example.demo.models.Job;

public class JobSpecs extends QueryToCriteria<Job> {
    public JobSpecs(String query) {
        super(query);
    }
}
