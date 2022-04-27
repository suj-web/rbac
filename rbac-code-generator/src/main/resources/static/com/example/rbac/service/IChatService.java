package com.example.rbac.service;

import com.example.rbac.pojo.Chat;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespPageBean;

/**
 * IChatService
 * @author suj
 * @since 2022-4-27
 */
public interface IChatService extends IService<Chat> {

    /**
     * 分页查询
     * @param currentPage
     * @param size
     * @return
     */
    RespPageBean getAllChat(Integer currentPage, Integer size);
}