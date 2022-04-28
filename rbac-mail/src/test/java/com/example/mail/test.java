package com.example.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * @Author suj
 * @create 2022/4/28
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class test {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void test() throws Exception{
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        //发件人
        helper.setFrom(mailProperties.getUsername());
        //收件人
        helper.setTo("541699620@qq.com");
        //发送日期
        helper.setSentDate(new Date());
        //主题
        helper.setSubject("入职信息");

        //邮件内容
        Context context = new Context();
        context.setVariable("name","suj");
        context.setVariable("posName","测试工程师");
        context.setVariable("joblevelName","初级工程师");
        context.setVariable("departmentName","技术部");
        String mail = templateEngine.process("mail", context);
        helper.setText(mail,true);
        javaMailSender.send(msg);
    }
}
