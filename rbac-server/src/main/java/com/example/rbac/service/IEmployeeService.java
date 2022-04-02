package com.example.rbac.service;

import com.example.rbac.pojo.*;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 根据username获取员工实体
     * @param username
     * @return
     */
    Employee getEmployeeByUserName(String username);

    /**
     * 分页查询员工信息
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
     * 获取最大工号
     * @return
     */
    RespBean maxWorkID();

    /**
     * 添加员工
     * @param employee
     * @return
     */
    RespBean addEmployee(Employee employee);

    /**
     * 获取员工信息
     * @param employeeId
     * @return
     */
    List<Employee> getEmployee(Integer employeeId);

    /**
     * 获取所有员工账套
     * @param currentPage
     * @param size
     * @param name
     * @return
     */
    RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size, String name);

    /**
     * 获取所有员工账套(不分页)
     * @return
     */
    List<Employee> getEmployeeWithSalary2();

    /**
     * 获取所有员工当月工资信息
     * @param currentPage
     * @param size
     * @param depId
     * @return
     */
    RespPageBean getAllEmployeeWithSalaryTable(Integer currentPage, Integer size, Integer depId);

    /**
     * 获取所有员工当月工资信息(不分页)
     * @param year
     * @param monthValue
     * @return
     */
    List<Employee> getAllEmployeeWithSalaryTable2(int year, int monthValue);

    /**
     * 获取所有工资表信息
     * @param currentPage
     * @param size
     * @param depId
     * @param localDate
     * @return
     */
    RespPageBean getAllSalaryTables(Integer currentPage, Integer size, Integer depId, LocalDate localDate);

    /**
     * 职业人数统计
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
     * 员工结构统计-按聘用形式统计
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

    /**
     * 员工异动信息统计
     * @param localDate
     * @param depId
     * @return
     */
    List<RespEmployeeRecordBean> getEmployeeTransaction(String localDate, Integer depId);

    /**
     * 合同到期提醒
     * @param currentPage
     * @param size
     * @return
     */
    RespPageBean getContractExpire(Integer currentPage, Integer size);

    /**
     * 合同到期数量
     * @return
     */
    Integer getContractExpireCount();

    /**
     * 生日提醒
     * @param currentPage
     * @param size
     * @return
     */
    RespPageBean getBirthdayRemind(Integer currentPage, Integer size);

    /**
     * 生日提醒数量
     * @return
     */
    Integer getBirthdayRemindCount();

    /**
     * 员工转正提醒
     * @param currentPage
     * @param size
     * @return
     */
    RespPageBean getConversionRemind(Integer currentPage, Integer size);

    /**
     * 员工转正提醒数量
     * @return
     */
    Integer getConversionRemindCount();
}
