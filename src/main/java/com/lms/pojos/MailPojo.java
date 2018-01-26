package com.lms.pojos;

import lombok.Data;

import java.util.List;

@Data
public class MailPojo {

    private List<String> to;

    private String subject;

    private String text;

}
