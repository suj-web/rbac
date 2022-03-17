package com.example.rbac.service;

import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.SysMsg;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
public interface ISysMsgService extends IService<SysMsg> {

    /**
     * 查询系统公告
     * @param currentPage
     * @param size
     * @param sysMsg
     * @return
     */
    RespPageBean getAllSystemMessage(Integer currentPage, Integer size, SysMsg sysMsg);
}
