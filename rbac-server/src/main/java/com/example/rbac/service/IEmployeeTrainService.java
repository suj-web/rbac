package com.example.rbac.service;

import com.example.rbac.pojo.EmployeeTrain;
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
public interface IEmployeeTrainService extends IService<EmployeeTrain> {

    /**
     * 获取员工培训信息
     * @param currentPage
     * @param size
     * @param name
     * @param localDate
     * @return
     */
    RespPageBean getAllEmployeeTrain(Integer currentPage, Integer size, String name, String localDate);
}
