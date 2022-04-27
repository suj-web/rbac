package com.example.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.pojo.Admin;
import com.example.rbac.pojo.Chat;
import com.example.rbac.pojo.ChatContent;
import com.example.rbac.pojo.Employee;
import com.example.rbac.service.IAdminService;
import com.example.rbac.service.IChatContentService;
import com.example.rbac.service.IChatService;
import com.example.rbac.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在线聊天
 *
 * @Author suj
 * @create 2022/1/21
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IChatService chatService;
    @Autowired
    private IChatContentService chatContentService;

    @ApiOperation(value = "获取所有管理员")
    @GetMapping("/admin")
    public List<Admin> getAllAdmins(String keywords) {
        return adminService.getAllAdmins(keywords);
    }

    @ApiOperation(value = "获取所有聊天消息")
    @GetMapping("/contents")
    public Map<String, List<ChatContent>> getChatContents() {
        return chatService.getChatContents();
    }

    @ApiOperation(value = "添加聊天消息")
    @PostMapping("/")
    public void addChatContent(@RequestBody ChatContent chatContent) {
        chatContent.setDate(LocalDateTime.now());
        chatContentService.addChatContent(chatContent);
    }
}
