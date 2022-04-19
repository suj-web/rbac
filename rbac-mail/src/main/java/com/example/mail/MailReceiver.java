package com.example.mail;

import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.MailConstants;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

@Component
@Slf4j
public class MailReceiver {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel){
        Employee employee = (Employee) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        //消息序号
        long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String msgId = (String) headers.get("spring_returned_message_correlation");

        HashOperations hashOperations = redisTemplate.opsForHash();

        try {
            if(hashOperations.entries("mail_log"+employee.getId()).containsKey(msgId)) {
                log.error("消息已经被消费=============>{}",msgId);
                /**
                 * 手动确认消息
                 * tag：消息序号
                 * multiple:是否确认多条
                 */
                channel.basicAck(tag,false);
                return;
            }
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg);
            //发件人
            helper.setFrom(mailProperties.getUsername());
            //收件人
            helper.setTo(employee.getEmail());
            //发送日期
            helper.setSentDate(new Date());
            //主题
            helper.setSubject("入职信息");

            //邮件内容
            Context context = new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("posName",employee.getPosition().getName());
            context.setVariable("joblevelName",employee.getJoblevel().getName());
            context.setVariable("departmentName",employee.getDepartment().getName());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail,true);
            javaMailSender.send(msg);
            log.info("邮件发送成功");
            hashOperations.put("mail_log"+employee.getId(),msgId,"OK");
            //手动确认消息
            channel.basicAck(tag, false);
        } catch (Exception e){
            /**
             * 手动确认消息
             * tag:消息序号
             * multiple:是否确认多条
             * requeue:是否退回到队列
             */
            try {
                channel.basicNack(tag, false, true);
            } catch (IOException ex) {
                log.error("邮件发送失败======={}",ex.getMessage());
            }
            log.error("邮件发送失败======={}",e.getMessage());
        }
    }
}
