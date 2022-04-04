package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.Salary;
import com.example.rbac.pojo.SalaryTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suj
 * @since 2022-02-10
 */
@Repository
public interface SalaryTableMapper extends BaseMapper<SalaryTable> {
    /**
     * 获取所有工资表信息
     * @param page
     * @param depId
     * @param localDate
     * @return
     */
    IPage<SalaryTable> getAllSalaryTables(Page<SalaryTable> page, @Param("depId") Integer depId, @Param("localDate") String localDate);

    /**
     * 获取所有员工当月工资信息
     * @param page
     * @param depId
     * @param localDate
     * @return
     */
    IPage<SalaryTable> getAllSalaryTableByCurrentMonth(Page<SalaryTable> page, @Param("depId") Integer depId, @Param("localDate") String localDate);

    /**
     * 获取所有员工当月工资信息导出到excel
     * @param localDate
     * @return
     */
    List<SalaryTable> getAllSalaryTablesForExcel(String localDate);
}
