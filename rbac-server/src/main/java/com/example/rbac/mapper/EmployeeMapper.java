package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rbac.pojo.RespBean;
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

    /**
     * 人员构成分析-按性别统计
     * @param depId
     * @return
     */
    List<RespChartBean> getCompositionByGender(Integer depId);

    /**
     * 人员构成分析-按最高学历统计
     * @param depId
     * @return
     */
    List<RespChartBean> getCompositionByDegree(Integer depId);

    /**
     * 部门薪资统计
     * @param localDate
     * @return
     */
    List<RespChartBean> getSalaryDepartment(String localDate);

    /**
     * 职位薪资统计
     * @param localDate
     * @return
     */
    List<RespChartBean> getSalaryPosition(String localDate);

    /**
     * 人事信息统计-员工结构统计-按聘用形式统计
     * @param depId
     * @return
     */
    List<RespChartBean> getCompositionByEngageForm(Integer depId);

    /**
     * 员工结构统计-按在职状态统计
     * @param depId
     * @return
     */
    List<RespChartBean> getCompositionByWorkState(Integer depId);

    /**
     * 年龄统计-员工平均年龄
     * @param depId
     * @return
     */
    Integer getAverageAge(Integer depId);

    /**
     * 年龄统计-部门员工平均年龄统计
     * @return
     */
    List<RespChartBean> getDepartmentAverageAge();

    /**
     * 工龄统计-员工平均工龄统计
     * @param depId
     * @return
     */
    Integer getAverageWorkAge(Integer depId);

    /**
     * 工龄统计-部门员工平均工龄统计
     * @return
     */
    List<RespChartBean> getDepartmentAverageWorkAge();

    /**
     * 按婚姻状况统计
     * @param depId
     * @return
     */
    List<RespChartBean> getWedLock(Integer depId);

    /**
     * 按民族进行统计
     * @param depId
     * @return
     */
    List<RespChartBean> getNation(Integer depId);

    /**
     * 按政治面貌进行统计
     * @param depId
     * @return
     */
    List<RespChartBean> getPoliticStatus(Integer depId);
}
