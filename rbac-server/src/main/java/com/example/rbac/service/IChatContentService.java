package com.example.rbac.service;

import com.example.rbac.pojo.ChatContent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespPageBean;

/**
 * IChatContentService
 * @author suj
 * @since 2022-4-27
 */
public interface IChatContentService extends IService<ChatContent> {

    /**
     * 分页查询
     * @param currentPage
     * @param size
     * @return
     */
    RespPageBean getAllChatContent(Integer currentPage, Integer size);

    void addChatContent(ChatContent chatContent);
}