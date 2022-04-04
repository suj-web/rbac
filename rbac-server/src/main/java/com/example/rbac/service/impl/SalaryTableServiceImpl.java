package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.Salary;
import com.example.rbac.pojo.SalaryTable;
import com.example.rbac.mapper.SalaryTableMapper;
import com.example.rbac.service.ISalaryTableService;
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
 * @since 2022-02-10
 */
@Service
public class SalaryTableServiceImpl extends ServiceImpl<SalaryTableMapper, SalaryTable> implements ISalaryTableService {

    @Autowired
    private SalaryTableMapper salaryTableMapper;

    /**
     * 获取所有工资表信息
     * @param currentPage
     * @param size
     * @param depId
     * @param localDate
     * @return
     */
    @Override
    public RespPageBean getAllSalaryTables(Integer currentPage, Integer size, Integer depId, String localDate) {
        Page<SalaryTable> page = new Page<>(currentPage, size);
        IPage<SalaryTable> salaryTableIPage = salaryTableMapper.getAllSalaryTables(page, depId, localDate);
        RespPageBean respPageBean = new RespPageBean(salaryTableIPage.getTotal(), salaryTableIPage.getRecords());
        return respPageBean;
    }

    /**
     * 获取所有员工当月工资信息
     * @param currentPage
     * @param size
     * @param depId
     * @param localDate
     * @return
     */
    @Override
    public RespPageBean getAllSalaryTableByCurrentMonth(Integer currentPage, Integer size, Integer depId, String localDate) {
        Page<SalaryTable> page = new Page<>(currentPage, size);
        IPage<SalaryTable> salaryTableIPage = salaryTableMapper.getAllSalaryTableByCurrentMonth(page, depId, localDate);
        RespPageBean respPageBean = new RespPageBean(salaryTableIPage.getTotal(), salaryTableIPage.getRecords());
        return respPageBean;
    }

    /**
     * 获取所有员工当月工资信息导出到excel
     * @param localDate
     * @return
     */
    @Override
    public List<SalaryTable> getAllSalaryTablesForExcel(String localDate) {
        return salaryTableMapper.getAllSalaryTablesForExcel(localDate);
    }
}
