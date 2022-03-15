package com.example.rbac.service;

import com.example.rbac.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.Role;
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
}
