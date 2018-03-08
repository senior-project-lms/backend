package com.lms.services.impl;

import com.lms.pojos.MailPojo;
import com.lms.services.interfaces.MailService;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
//
    @Autowired
    private JavaMailSender mailSender;


    @Async
    @Override
    public void send(MailPojo pojo) throws MailException, InterruptedException {

        SimpleMailMessage message = new SimpleMailMessage();
        String[] emailAddresses = pojo.getTo().toArray(new String[0]);
        message.setTo(emailAddresses);
        message.setSubject(pojo.getSubject());
        message.setText(Jsoup.parse(pojo.getText()).text());

        mailSender.send(message);
    }
}
