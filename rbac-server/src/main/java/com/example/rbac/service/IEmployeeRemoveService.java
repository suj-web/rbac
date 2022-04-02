package com.example.rbac.service;

import com.example.rbac.pojo.EmployeeRemove;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;

import java.time.LocalDate;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
public interface IEmployeeRemoveService extends IService<EmployeeRemove> {

    /**
     * 获取所有员工调动资料
     * @param currentSize
     * @param size
     * @param remove
     * @param localDate
     * @return
     */
    RespPageBean getAllEmployeeRemove(Integer currentSize, Integer size, EmployeeRemove remove, String localDate);

    /**
     * 添加员工调动信息
     * @param employeeRemove
     * @return
     */
    RespBean addEmployeeRemove(EmployeeRemove employeeRemove);

    /**
     * 修改员工调动信息
     * @param employeeRemove
     * @return
     */
    RespBean updateEmployeeRemove(EmployeeRemove employeeRemove);
}
