package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rbac.pojo.RespChartBean;
import com.example.rbac.pojo.Role;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
     * 分页查询员工信息
     * @param page
     * @param employee
     * @param beginDateScope
     * @return
     */
    IPage<Employee> getEmployeeByPage(Page<Employee> page, @Param("employee") Employee employee, @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
     * 获取员工信息
     * @param employeeId
     * @return
     */
    List<Employee> getEmployee(Integer employeeId);

    /**
     * 获取所有员工账套
     * @param page
     * @param name
     * @return
     */
    IPage<Employee> getEmployeeWithSalary(Page<Employee> page, @Param("name") String name);

    /**
     * 获取所有员工账套(不分页)
     */
    List<Employee> getEmployeeWithSalary2();

    /**
     * 获取所有员工当月工资信息
     * @param page
     * @param depId
     * @param year
     * @param monthValue
     * @return
     */
    IPage<Employee> getAllEmployeeWithSalaryTable(Page<Employee> page, @Param("depId") Integer depId, @Param("year") Integer year, @Param("month") Integer monthValue);

    /**
     * 获取所有员工当月工资信息(不分页)(用于导出excel)
     * @return
     */
    List<Employee> getAllEmployeeWithSalaryTable2(@Param("year") Integer year, @Param("month") Integer month);

    /**
     * 职位人员统计
     * @return
     */
    List<RespChartBean> getPositionNumber();

    /**
     * 部门人员统计
     * @return
     */
    List<RespChartBean> getDepartmentNumber();

    /**
     * 人员构成分析-按年龄段统计
     * @param depId
     * @return
     */
    List<RespChartBean> getCompositionByAge(Integer depId);

    /**
     * 人员构成分析-按工龄统计
     * @param depId
     * @return
     */
    List<RespChartBean> getCompositionByWorkAge(Integer depId);
}
