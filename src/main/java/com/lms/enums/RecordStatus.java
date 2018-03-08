package com.lms.enums;

public enum RecordStatus {

    ACCEPTED(1),
    REJECTED(2),
    CANCELLED(3);

    public long CODE;

    RecordStatus(long CODE) {
        this.CODE = CODE;
    }


}
