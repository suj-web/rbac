package com.example.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IChatContentService;
import com.example.rbac.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * websocket
 *
 * @Author suj
 * @create 2022/1/21
 */
@Controller
public class WsController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private IChatContentService chatContentService;
    @Autowired
    private IChatService chatService;

    @MessageMapping("/ws/chat")
    public void handleMsg(Authentication authentication, ChatMessage chatMessage) {
        Admin admin = (Admin) authentication.getPrincipal();
        chatMessage.setFrom(admin.getUsername());
        chatMessage.setFromNickName(admin.getName());
        chatMessage.setDate(LocalDateTime.now());

        Chat chatObj = chatService.getOne(new QueryWrapper<Chat>().eq("chat_obj", chatMessage.getTo() + "$" + admin.getUsername()));
        if(null == chatObj) {
            chatObj = new Chat();
            chatObj.setChatObj(chatMessage.getTo() + "$" + admin.getUsername());
            chatService.save(chatObj);
        }
        ChatContent chatContent = new ChatContent();
        chatContent.setChatObjId(chatObj.getId());
        chatContent.setContent(chatMessage.getContent());
        chatContent.setDate(LocalDateTime.now());
        chatContentService.save(chatContent);
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getTo(),"/queue/chat",chatMessage);
    }
}
