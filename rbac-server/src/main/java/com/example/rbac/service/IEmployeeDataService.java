package com.example.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.EmployeeData;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-04-25
 */
public interface IEmployeeDataService extends IService<EmployeeData> {

    /**
     * 查询员工高级资料
     * @param currentPage
     * @param size
     * @param depId
     * @param name
     * @return
     */
    RespPageBean getAllEmpData(Integer currentPage, Integer size, Integer depId, String name);

    /**
     * 修改员工高级资料
     * @param employeeData
     * @return
     */
    RespBean updateEmpData(EmployeeData employeeData);
}
