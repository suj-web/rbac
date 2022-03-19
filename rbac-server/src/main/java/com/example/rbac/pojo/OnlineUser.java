package com.example.rbac.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author suj
 * @create 2022/3/19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineUser {
    /**
     * 会话id
     */
    private String sessionId;
    /**
     * 登录名
     */
    private String loginName;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 登录地址
     */
    private String address;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 登录时间
     */
    private LocalDateTime loginTime;
}
