package com.example.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
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

    @OperationLogAnnotation(operModul = "在线聊天", operType = "新增", operDesc = "发送消息")
    @ApiOperation(value = "添加聊天消息")
    @PostMapping("/")
    public void addChatContent(@RequestBody ChatContent chatContent) {
        chatContent.setDate(LocalDateTime.now());
        chatContentService.addChatContent(chatContent);
    }

    @ApiOperation(value = "获取所有未读信息的数量")
    @GetMapping("/unread/count")
    public Map<String, Integer> getUnReadCount() {
        Map<String, Integer> map = new HashMap<>();
        List<Chat> chatObjs = chatService.list();
        for(Chat chat: chatObjs) {
            Integer count = chatContentService.count(new QueryWrapper<ChatContent>().eq("chat_obj_id", chat.getId()).eq("status", false));
            map.put(chat.getChatObj(), count);
        }
        return map;
    }

    @OperationLogAnnotation(operModul = "在线聊天", operType = "更新", operDesc = "查看消息")
    @ApiOperation(value = "修改消息状态(改为已读)")
    @PutMapping("/message/")
    public void updateMessageStatus(String chatObj) {
        Chat chat = chatService.getOne(new QueryWrapper<Chat>().eq("chat_obj", chatObj));
        if(null == chat) {
            return;
        }
        List<ChatContent> list = chatContentService.list(new QueryWrapper<ChatContent>().eq("chat_obj_id", chat.getId()).eq("status", false));
        for(ChatContent chatContent: list) {
            chatContent.setStatus(true);
            chatContentService.updateById(chatContent);
        }
    }
}
