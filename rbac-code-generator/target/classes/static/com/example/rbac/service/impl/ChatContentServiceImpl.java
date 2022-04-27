package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.mapper.ChatContentMapper;
import com.example.rbac.pojo.ChatContent;
import com.example.rbac.service.IChatContentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ChatContentServiceImpl
 * @author suj
 * @since 2022-4-27
 */
@Service
public class ChatContentServiceImpl extends ServiceImpl<ChatContentMapper, ChatContent> implements IChatContentService {

    @Autowired
    private ChatContentMapper chatContentMapper;

    /**
     * 分页查询
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public RespPageBean getAllChatContent(Integer currentPage, Integer size) {
        Page<ChatContent> page = new Page<>(currentPage, size);
        IPage<ChatContent> chatContentIPage = chatContentMapper.selectPage(page,null);
        RespPageBean respPageBean = new RespPageBean(chatContentIPage.getTotal(), chatContentIPage.getRecords())
        return respPageBean;
    }
}
