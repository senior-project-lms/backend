package com.lms.enums;

public enum ECoursePrivilege {


    // PAGE COURSE PRIVILEGES
    PAGE_COURSE(1010101010),
    PAGE_COURSE_ANNOUNCEMENT(1111011101),
    PAGE_COURSE_GRADES(1111001001),
    PAGE_COURSE_ASSIGNMENTS(1110101000),
    PAGE_COURSE_QT(1111001110),
    PAGE_COURSE_RESOURCES(1110000101),
    PAGE_COURSE_QA(1111010101),
    PAGE_COURSE_CALENDAR(1111010001),
    PAGE_COURSE_USERS(1110000010),
    PAGE_COURSE_ENROLLMENT_REQUEST(1101010101),
    PAGE_COURSE_ENROLLED_STUDENTS(1101110001),
    PAGE_COURSE_AUTHENTICATED_USERS(1101111100),
    PAGE_COURSE_SETTINGS(1111110011),


//1000101111

    // COURSE ANNOUNCEMENT
    SAVE_COURSE_ANNOUNCEMENT(1110010101),
    DELETE_COURSE_ANNOUNCEMENT(1111000111),
    UPDATE_COURSE_ANNOUNCEMENT(1111010110),
    READ_COURSE_ANNOUNCEMENT(1110101110),
    // COURSE GRADE
    SAVE_COURSE_GRADE(1110110110),
    DELETE_COURSE_GRADE(1111001101),
    UPDATE_COURSE_GRADE(1111010111),
    READ_COURSE_GRADE(1110010011),

    // COURSE SCORE
    SAVE_COURSE_SCORE(1110110001),
    DELETE_COURSE_SCORE(1110100011),
    UPDATE_COURSE_SCORE(1110001011),
    READ_ALL_COURSE_SCORES(1110100010),
    READ_OWN_COURSE_SCORES(1010000001),



    // COURSE ASSIGNMENT
    SAVE_COURSE_ASSIGNMENT(1110001111),
    DELETE_COURSE_ASSIGNMENT(1110101101),
    UPDATE_COURSE_ASSIGNMENT(1110011100),
    READ_ALL_COURSE_ASSIGNMENTS(1110000011),
    READ_OWN_COURSE_ASSIGNMENTS(1110000001),
    UPLOAD_OWN_COURSE_ASSIGNMENT_FILE(1110100000),
    UPLOAD_COURSE_ASSIGNMENT_FILE(1111100110),
    DELETE_OWN_COURSE_ASSIGNMENT_FILE(1110110111),
    DELETE_COURSE_ASSIGNMENT_FILE(1110101011),



    // COURSE QT
    SAVE_COURSE_QT(1111111111),
    DELETE_COURSE_QT(1110111010),
    UPDATE_COURSE_QT(1111000000),
    VERIFY_COURSE_QT(1110010010),
    JOIN_COURSE_QT(1111000101),
    READ_COURSE_QT(1101000011),

    // COURSE RESOURCE
    SAVE_COURSE_RESOURCE(1110011010),
    DELETE_COURSE_RESOURCE(1110100101),
    UPDATE_COURSE_RESOURCE(1110011110),
    READ_ALL_COURSE_RESOURCES(1110010001),
    UPLOAD_COURSE_RESOURCE_FILE(1110011001),
    DELETE_COURSE_RESOURCE_FILE(1111001111),


    // COURSE CourseQA
    SAVE_COURSE_QA(1111011100),
    DELETE_COURSE_QA(1110110101),
    DELETE_OWN_COURSE_QA(1111101111),
    UPDATE_OWN_COURSE_QA(1010011110),

    UPDATE_COURSE_QA(1110001010),
    VERIFY_COURSE_QA(1111111000),
    VOTE_COURSE_QA(1110111110),
    STAR_COURSE_QA(1111100000),


    // Course Calendar
    SAVE_COURSE_CALENDAR(1111010000),
    DELETE_COURSE_CALENDAR(1111110111),
    UPDATE_COURSE_CALENDAR(1111111110),
    READ_COURSE_CALENDAR(1111101010),

    // Course Users
    READ_REGISTERED_STUDENTS(1111101101),
    READ_ENROLLMENT_REQUESTS(1110111101),
    REJECT_ENROLLMENT_REQUEST(1111010100),
    APPROVE_ENROLLMENT_REQUEST(1111000001),

    READ_AUTHENTICATED_USERS(1101011111),
    SAVE_AUTHENTICATED_USER(1110111000),
    DELETE_AUTHENTICATED_USER(1110000111),
    UPDATE_AUTHENTICATED_USER(1011101010),




    // Enrolment
    ENROLL_COURSE(1111110110),
    READ_REQUESTED_ENROLLMENT_REQUESTS(1111111010),
    CANCEL_ENROLLMENT_REQUEST(1111011010),


    READ_NOT_REGISTERED_COURSES(1110100110),
    READ_REGISTERED_COURSES(1111100101),
    READ_AUTHENTICATED_COURSES(1110001110)



    ;


//
//1100001000
//1101110001
//1101001110
//
//

    public long CODE;


    ECoursePrivilege(long CODE) {
        this.CODE = CODE;
    }

}
