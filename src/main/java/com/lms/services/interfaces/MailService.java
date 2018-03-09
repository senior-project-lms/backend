package com.lms.services.interfaces;


import com.lms.pojos.MailPojo;
import org.springframework.mail.MailException;

public interface MailService {

    void send(MailPojo pojo) throws MailException, InterruptedException;
}
