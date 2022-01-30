package com.example.rbac.service;

import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.SalaryAdjust;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-01-12
 */
public interface ISalaryAdjustService extends IService<SalaryAdjust> {

    /**
     * 获取所有员工调薪信息
     * @param currentPage
     * @param size
     * @param name
     * @param workId
     * @return
     */
    RespPageBean getAllSalaryAdjust(Integer currentPage, Integer size, String name, String workId);
}
