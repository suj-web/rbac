package com.example.rbac.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.MailConstants;
import com.example.rbac.pojo.MailLog;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.IMailLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 邮件发送定时任务
 *
 * @Author suj
 * @create 2022/1/21
 */
@Component
@EnableScheduling
@Slf4j
public class MailTask {

    @Autowired
    private IMailLogService mailLogService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 邮件发送定时任务
     * 10秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void mailTask() {
        List<MailLog> list = mailLogService.list(new QueryWrapper<MailLog>().eq("status", 0).lt("try_time", LocalDateTime.now()));
        list.forEach(mailLog -> {
            //如果重试次数超过3次，更新状态为投递失败，不再重试
            if(3<=mailLog.getCount()) {
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",2).eq("msg_id",mailLog.getMsgId()));
            } else {
                mailLogService.update(new UpdateWrapper<MailLog>().set("count", mailLog.getCount() + 1)
                        .set("try_time", LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT))
                        .eq("msg_id", mailLog.getMsgId()));
                Employee emp = employeeService.getEmployee(mailLog.getEmployeeId()).get(0);
                //发送消息
                rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME, MailConstants.MAIL_QUEUE_NAME,
                        emp, new CorrelationData(mailLog.getMsgId()));
            }
        });
        log.info("定时发送邮件");
    }
}
