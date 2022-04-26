package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.mapper.EmployeeMapper;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.SalaryAdjust;
import com.example.rbac.mapper.SalaryAdjustMapper;
import com.example.rbac.service.ISalaryAdjustService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-01-12
 */
@Service
public class SalaryAdjustServiceImpl extends ServiceImpl<SalaryAdjustMapper, SalaryAdjust> implements ISalaryAdjustService {

    @Autowired
    private SalaryAdjustMapper salaryAdjustMapper;
    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 获取所有员工调薪信息
     * @param currentPage
     * @param size
     * @param depId
     * @param localDate
     * @return
     */
    @Override
    public RespPageBean getAllSalaryAdjust(Integer currentPage, Integer size, Integer depId, String localDate) {
        Page<SalaryAdjust> page = new Page<>(currentPage, size);
        IPage<SalaryAdjust> salaryAdjustIPage = salaryAdjustMapper.getAllSalaryAdjust(page, depId, localDate);
        List<SalaryAdjust> records = salaryAdjustIPage.getRecords();
        for (SalaryAdjust salaryAdjust: records) {
            Integer salaryId = employeeMapper.selectById(salaryAdjust.getEmployeeId()).getSalaryId();
            if(salaryId == salaryAdjust.getAfterSalaryId()) {
                salaryAdjust.setIsAdjust(true);
            }
        }
        return new RespPageBean(salaryAdjustIPage.getTotal(), records);
    }
}
