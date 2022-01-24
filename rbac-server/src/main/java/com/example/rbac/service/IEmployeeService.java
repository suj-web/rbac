package com.example.rbac.service;

import com.example.rbac.pojo.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.Role;
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
     * 根据员工id获取角色
     * @param id
     * @return
     */
    List<Role> getRolesByEmployeeId(Integer id);

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
     * 更新员工密码
     * @param oldPass
     * @param pass
     * @param employeeId
     * @return
     */
    RespBean updateEmployeePassword(String oldPass, String pass, Integer employeeId);

    /**
     * 更新员工头像
     * @param url
     * @param id
     * @param authentication
     * @return
     */
    RespBean updateEmployeeUserFace(String url, Integer id, Authentication authentication);

    /**
     * 获取所有员工账套
     * @param currentPage
     * @param size
     * @return
     */
    RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);

    /**
     * 根据关键字获取员工
     * @param keywords
     * @return
     */
    List<Employee> getAllEmployees(String keywords);

    /**
     * 获取所有员工信息(带用户角色)
     * @param currentPage
     * @param size
     * @param name
     * @return
     */
    RespPageBean getEmployeeWithRoleByPage(Integer currentPage, Integer size, String name);

    /**
     * 更新员工角色
     * @param employeeId
     * @param ids
     * @return
     */
    RespBean updateEmployeeRole(Integer employeeId, Integer[] ids);
}
