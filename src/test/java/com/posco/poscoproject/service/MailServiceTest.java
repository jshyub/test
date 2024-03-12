package com.posco.poscoproject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailServiceTest {

    @Autowired
    MailService mailService;

    @Test
    public void sendMail() throws Exception {
        String pw = mailService.sendSimpleMessage("wkdtpwhs@naver.com", "id", "passwd");

    }
}