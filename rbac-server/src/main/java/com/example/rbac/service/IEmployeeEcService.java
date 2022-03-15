package com.example.rbac.service;

import com.example.rbac.pojo.EmployeeEc;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespPageBean;

import java.time.LocalDate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
public interface IEmployeeEcService extends IService<EmployeeEc> {

    /**
     * 获取所有奖惩人员信息
     * @param currentPage
     * @param size
     * @param name
     * @param localDate
     * @return
     */
    RespPageBean getAllEmployeeEc(Integer currentPage, Integer size, String name, String localDate);
}
