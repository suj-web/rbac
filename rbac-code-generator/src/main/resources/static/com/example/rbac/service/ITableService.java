package com.example.rbac.service;

import com.example.rbac.pojo.Table;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespPageBean;

/**
 * ITableService
 * @author suj
 * @since 2022-4-29
 */
public interface ITableService extends IService<Table> {

    /**
     * 分页查询
     * @param currentPage
     * @param size
     * @return
     */
    RespPageBean getAllTable(Integer currentPage, Integer size);
}