package com.example.rbac.service;

import com.example.rbac.pojo.Salary;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
public interface ISalaryService extends IService<Salary> {

    /**
     * 获取所有员工工资信息
     * @param salaryId
     * @param depId
     * @return
     */
    List<Salary> getAllSalary(Integer salaryId, Integer depId);
}
