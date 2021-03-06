package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.SalaryAdjust;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suj
 * @since 2022-01-12
 */
@Repository
public interface SalaryAdjustMapper extends BaseMapper<SalaryAdjust> {

    /**
     * 获取所有员工调薪信息
     * @param page
     * @param name
     * @param localDate
     * @return
     */
    IPage<SalaryAdjust> getAllSalaryAdjust(Page<SalaryAdjust> page, @Param("name") String name, @Param("localDate") String localDate);

    /**
     * 根据员工id获取调薪信息
     * @param employeeId
     * @return
     */
    List<SalaryAdjust> getSalaryAdjustByEmployeeId(Integer employeeId);
}
