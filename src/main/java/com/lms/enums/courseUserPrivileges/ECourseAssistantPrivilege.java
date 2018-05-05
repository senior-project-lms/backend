package com.lms.enums.courseUserPrivileges;

public enum ECourseAssistantPrivilege {


    // PAGE COURSE PRIVILEGES
    PAGE_COURSE(1010101010),
    PAGE_COURSE_ANNOUNCEMENT(1111011101),
    PAGE_COURSE_GRADES(1111001001),
    PAGE_COURSE_ASSIGNMENTS(1110101000),
    PAGE_COURSE_QT(1111001110),
    PAGE_COURSE_RESOURCES(1110000101),
    PAGE_COURSE_QA(1111010101),
    PAGE_COURSE_CALENDAR(1111010001),


    // COURSE ANNOUNCEMENT
    SAVE_COURSE_ANNOUNCEMENT(1110010101),
    DELETE_COURSE_ANNOUNCEMENT(1111000111),
    UPDATE_COURSE_ANNOUNCEMENT(1111010110),
    READ_COURSE_ANNOUNCEMENT(1110101110),

    // COURSE GRADE
    READ_COURSE_GRADE(1110010011),


    // COURSE SCORE
    READ_OWN_COURSE_SCORES(1010000001),



    // COURSE ASSIGNMENT
    READ_ALL_COURSE_ASSIGNMENTS(1110000011),



    // COURSE QT
    SAVE_COURSE_QT(1111111111),
    DELETE_COURSE_QT(1110111010),
    UPDATE_COURSE_QT(1111000000),
    VERIFY_COURSE_QT(1110010010),
    //JOIN_COURSE_QT(1111000101),
    READ_COURSE_QT(1101000011),
    PUBLISH_COURSE_QT(1000100100),
    READ_NOT_PUBLISHED_COURSE_QT(1000111000),

    // COURSE RESOURCE
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




    SAVE_COURSE_CALENDAR(1110100001),
    DELETE_COURSE_CALENDAR(1111110111),
    UPDATE_COURSE_CALENDAR(1111111110),
    READ_COURSE_CALENDAR(1111101010),

    // Enrolment
    ENROLL_COURSE(1111110110),
    READ_ENROLLMENT_REQUESTS(1110111101),
    READ_REQUESTED_ENROLLMENT_REQUESTS(1111111010),
    CANCEL_ENROLLMENT_REQUEST(1111011010),
    READ_NOT_REGISTERED_COURSES(1110100110),
    READ_REGISTERED_COURSES(1111100101),
    READ_AUTHENTICATED_COURSES(1110001110)


    ;



    public long CODE;


    ECourseAssistantPrivilege(long CODE) {
        this.CODE = CODE;
    }
}
