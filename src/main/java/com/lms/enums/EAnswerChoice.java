package com.lms.enums;

/**
 * Created by umit.kas on 05.12.2017.
 */
public enum EAnswerChoice {

    TEXT(1), SELECT_MULTI(2), SELECT_SINGLE(3);

    private int CODE;

    EAnswerChoice(int code) {
        this.CODE = code;
    }
}
