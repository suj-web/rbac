package com.example.rbac.service;

import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.Salary;
import com.example.rbac.pojo.SalaryTable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-02-10
 */
public interface ISalaryTableService extends IService<SalaryTable> {

    /**
     * 获取所有工资表信息
     * @param currentPage
     * @param size
     * @param depId
     * @param localDate
     * @return
     */
    RespPageBean getAllSalaryTables(Integer currentPage, Integer size, Integer depId, String localDate);

    /**
     * 获取所有员工当月工资信息
     * @param currentPage
     * @param size
     * @param depId
     * @param format
     * @return
     */
    RespPageBean getAllSalaryTableByCurrentMonth(Integer currentPage, Integer size, Integer depId, String format);

    /**
     * 获取所有员工当月工资信息导出到excel
     * @param format
     * @return
     */
    List<SalaryTable> getAllSalaryTablesForExcel(String format);
}
