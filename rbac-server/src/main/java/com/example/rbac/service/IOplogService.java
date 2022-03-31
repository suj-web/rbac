package com.example.rbac.service;

import com.example.rbac.pojo.Oplog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
public interface IOplogService extends IService<Oplog> {

    /**
     * 查询操作日志
     * @param currentPage
     * @param size
     * @param oplog
     * @param operDateTime
     * @return
     */
    RespPageBean getAllOplogs(Integer currentPage, Integer size, Oplog oplog, LocalDate[] operDateTime);
}
