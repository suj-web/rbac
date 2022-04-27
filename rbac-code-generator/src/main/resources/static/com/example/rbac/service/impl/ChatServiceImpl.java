package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.mapper.ChatMapper;
import com.example.rbac.pojo.Chat;
import com.example.rbac.service.IChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ChatServiceImpl
 * @author suj
 * @since 2022-4-27
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements IChatService {

    @Autowired
    private ChatMapper chatMapper;

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
        RespPageBean respPageBean = new RespPageBean(chatIPage.getTotal(), chatIPage.getRecords())
        return respPageBean;
    }
}
