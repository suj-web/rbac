package com.example.rbac.service.impl;

import com.example.rbac.pojo.Salary;
import com.example.rbac.mapper.SalaryMapper;
import com.example.rbac.service.ISalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements ISalaryService {

    @Autowired
    private SalaryMapper salaryMapper;
    /**
     * 获取所有员工工资信息
     * @param salaryId
     * @param depId
     * @return
     */
    @Override
    public List<Salary> getAllSalary(Integer salaryId, Integer depId) {

        return salaryMapper.getAllSalary(salaryId, depId);
    }
}
