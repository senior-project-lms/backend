package com.lms.enums.commonUserPrivileges;

public enum CommonAssistantPrivilege {



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
    PAGE_GLOBAL_RESOURCES(1110011000);


    public long CODE;


    CommonAssistantPrivilege(long CODE) {
        this.CODE = CODE;
    }
}