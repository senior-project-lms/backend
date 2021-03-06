package com.lms.enums.commonUserPrivileges;

public enum CommonStudentPrivilege {


    GLOBAL_ACCESS(1111100001),



    // System Announcement
    READ_SYSTEM_ANNOUNCEMENT(1111101100),







    // PAGE PRIVILEGES

    PAGE_HOME(1110101111),
    PAGE_COURSES(1111000011),
    PAGE_GLOBAL_CALENDAR(1110011111),
    PAGE_GLOBAL_QA(1110001001),
    PAGE_SYSTEM_ANNOUNCEMENT(1111110000),
    PAGE_OWN_PROFILE(1110100001),
    PAGE_SETTINGS(1111110101),
    PAGE_GLOBAL_RESOURCES(1110011000),


    READ_GLOBAL_CALENDAR(1010101001),

    SAVE_GLOBAL_QA(1100011001),
    DELETE_OWN_GLOBAL_QA(1011011001),
    UPDATE_OWN_GLOBAL_QA(1000000101),

    UPDATE_GLOBAL_QA(1010000100),
    VOTE_GLOBAL_QA(1101110101),
    STAR_COURSE_QA(1010101100)
    ;


    public long CODE;


    CommonStudentPrivilege(long CODE) {
        this.CODE = CODE;
    }
}
