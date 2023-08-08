package com.example.demo.enums;

public enum Authorities {
    USER, RECRUITER;

    // request pre- and post- security check constants
    public static final String USER_PREAUTHORIZE = "hasAuthority('USER')";

    public static final String RECRUITER_PREAUTHORIZE = "hasAuthority('RECRUITER')";

}
