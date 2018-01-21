package com.lms.properties;

/**
 * Created by umit.kas on 28.11.2017.
 */
public enum Privileges {

    SAVE_SYSTEM_ANNOUNCEMENT(1000000001),
    READ_SYSTEM_ANNOUNCEMENT(1000000010),
    DELETE_SYSTEM_ANNOUNCEMENT(1000000100),
    UPDATE_SYSTEM_ANNOUNCEMENT(1000001000);

    public long CODE;

    Privileges(long CODE) {
        this.CODE = CODE;
    }

    }
