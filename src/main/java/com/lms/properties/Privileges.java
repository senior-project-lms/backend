package com.lms.properties;

/**
 * Created by umit.kas on 28.11.2017.
 */
public enum Privileges {

    // System Announcement
    SAVE_SYSTEM_ANNOUNCEMENT(1000000001),
    READ_SYSTEM_ANNOUNCEMENT(1000000010),
    DELETE_SYSTEM_ANNOUNCEMENT(1000000100),
    UPDATE_SYSTEM_ANNOUNCEMENT(1000001000),

    // User
    SAVE_USER(1000000011),
    READ_ALL_USERS(1000000110),
    DELETE_USER(1000001100),
    UPDATE_USER(1000011000),

    // Course
    SAVE_COURSE(1000000011),
    READ_ALL_COURSES(1000000110),
    DELETE_COURSE(1000001100),
    UPDATE_COURSE(1000011000),;



    public long CODE;

    Privileges(long CODE) {
        this.CODE = CODE;
    }

    }
