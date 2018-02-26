package com.lms.enums;

/**
 * Created by umit.kas on 28.11.2017.
 */
public enum EPrivilege {

    // Home
    GLOBAL_ACCESS(11100001),
//    READ_HOME_DATA(11100000),
//    UPDATE_HOME_DATA(11101110),
//    ADD_FAVORITE_DATA(11100001),

    // System Announcement
    SAVE_SYSTEM_ANNOUNCEMENT(10111001),
    READ_SYSTEM_ANNOUNCEMENT(11101100),
    DELETE_SYSTEM_ANNOUNCEMENT(11101110),
    UPDATE_SYSTEM_ANNOUNCEMENT(10001100),
    UPLOAD_SYSTEM_ANNOUNCEMENT_FILE(10110010),
    DELETE_SYSTEM_ANNOUNCEMENT_FILE(10010111),


    // User
    SAVE_USER(10101100),
    READ_ALL_USERS(11000100),
    READ_USER(11111011),
    DELETE_USER(11000010),
    UPDATE_USER(10110011),

    // Course
    SAVE_COURSE(11011111),
    READ_ALL_COURSES(10111111),
    DELETE_COURSE(11111101),
    UPDATE_COURSE(10010000),
    ACCESS_COURSES_PAGE(10000110),
    READ_REGISTERED_COURSES(11100101),
    READ_AUTHENTICATED_COURSES(10001110),
    READ_COURSES_BY_VISIBILITY(11011011),
    READ_COURSE_STATUSES(11110100),
    UPDATE_COURSE_VISIBILITY(10000000),
    READ_NOT_REGISTERED_COURSES(10100110),
    READ_REGISTERED_STUDENTS(11010011),


    // Authority
    SAVE_AUTHORITY(11101000),
    READ_ALL_AUTHORITIES(10010100),
    DELETE_AUTHORITY(11111001),
    UPDATE_AUTHORITY(10001101),


    // Privilege
    READ_ALL_PRIVILEGES(11000110),



    // Default Authority
    READ_DEFAULT_AUTHORITIES_AND_PRIVILEGES(10000100),
    UPDATE_DEFAULT_AUTHORITY(10101010),
    SAVE_DEFAULT_AUTHORITY(11001010),
    DELETE_DEFAULT_AUTHORITY(11100010),


    // Enrolment
    ENROLL_COURSE(11110110),
    APPROVE_ENROLLMENT_REQUEST(11000001),
    READ_ENROLLMENT_REQUESTS(10111101),
    READ_REQUESTED_ENROLLMENT_REQUESTS(11111010),
    CANCEL_ENROLLMENT_REQUEST(11011010),
    REJECT_ENROLLMENT_REQUEST(11010100),

//
//
//    10010101 11000111
//
//

    // PAGE PRIVILEGES

    PAGE_USER_FOR_ADMIN(10011011),
    PAGE_COURSE_FOR_ADMIN(11011001),
    PAGE_AUTHORITIES(11111100),
    PAGE_HOME(10101111),
    PAGE_COURSES(11000011),
    PAGE_GLOBAL_CALENDAR(10011111),
    PAGE_GLOBAL_QA(10001001),
    PAGE_SYSTEM_ANNOUNCEMENT(11110000),
    PAGE_USER_DETAILS(10011101),
    PAGE_OWN_PROFILE(10100001),
    PAGE_SETTINGS(11110101),
    PAGE_GLOBAL_RESOURCES(10011000),

    // PAGE COURSE PRIVILEGES
    PAGE_COURSE_ANNOUNCEMENT(11011101),
    PAGE_COURSE_GRADES(11001001),
    PAGE_COURSE_ASSIGNMENTS(10101000),
    PAGE_COURSE_QT(11001110),
    PAGE_COURSE_RESOURCES(10000101),
    PAGE_COURSE_QA(11010101),
    PAGE_COURSE_CALENDAR(11010001),
    PAGE_COURSE_STUDENTS(10000010),
    PAGE_COURSE_SETTINGS(11110011);

    public long CODE;


    EPrivilege(long CODE) {
        this.CODE = CODE;
    }

}
