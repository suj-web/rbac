package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.mapper.ChatContentMapper;
import com.example.rbac.mapper.ChatMapper;
import com.example.rbac.pojo.Chat;
import com.example.rbac.pojo.ChatContent;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ChatServiceImpl
 * @author suj
 * @since 2022-4-27
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements IChatService {

    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private ChatContentMapper chatContentMapper;

    /**
     * 分页查询
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public RespPageBean getAllChat(Integer currentPage, Integer size) {
        Page<Chat> page = new Page<>(currentPage, size);
        IPage<Chat> chatIPage = chatMapper.selectPage(page,null);
        RespPageBean respPageBean = new RespPageBean(chatIPage.getTotal(), chatIPage.getRecords());
        return respPageBean;
    }

    @Override
    public Map<String, List<ChatContent>> getChatContents() {
        List<Chat> chats = chatMapper.selectList(null);
        Map<String, List<ChatContent>> map = new HashMap<>();
        for(Chat chat: chats) {
            List<ChatContent> chatContents = chatContentMapper.selectList(new QueryWrapper<ChatContent>().eq("chat_obj_id", chat.getId()));
            map.put(chat.getChatObj(), chatContents);
        }
        return map;
    }
}
