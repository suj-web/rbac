package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.mapper.EmployeeMapper;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.EmployeeRemove;
import com.example.rbac.mapper.EmployeeRemoveMapper;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IEmployeeRemoveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
@Service
public class EmployeeRemoveServiceImpl extends ServiceImpl<EmployeeRemoveMapper, EmployeeRemove> implements IEmployeeRemoveService {


    @Autowired
    private EmployeeRemoveMapper employeeRemoveMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 获取所有员工调动资料
     * @param currentSize
     * @param size
     * @param name
     * @param workId
     * @return
     */
    @Override
    public RespPageBean getAllEmployeeRemove(Integer currentSize, Integer size, String name, String workId) {
        Page<EmployeeRemove> page = new Page<>(currentSize, size);
        IPage<EmployeeRemove> employeeRemoveIPage = employeeRemoveMapper.getAllEmployeeRemove(page, name, workId);
        return new RespPageBean(employeeRemoveIPage.getTotal(), employeeRemoveIPage.getRecords());
    }

    /**
     * 添加员工调动信息
     * @param employeeRemove
     * @return
     */
    @Override
    @Transactional
    public RespBean addEmployeeRemove(EmployeeRemove employeeRemove) {
        if(1 != employeeRemoveMapper.insert(employeeRemove)) {
            return RespBean.error("添加失败");
        }
        try {
            Employee employee = employeeMapper.selectById(employeeRemove.getEmployeeId());
            employee.setDepartmentId(employeeRemove.getAfterDepartmentId());
            employee.setPositionId(employeeRemove.getAfterPositionId());
            employeeMapper.updateById(employee);
            return RespBean.success("添加成功");
        } catch (Exception e) {
            return RespBean.error("添加失败");
        }
    }

    @Override
    @Transactional
    public RespBean updateEmployeeRemove(EmployeeRemove employeeRemove) {
        if(1 != employeeRemoveMapper.updateById(employeeRemove)) {
            return RespBean.error("更新失败");
        }
        try {
            Employee employee = employeeMapper.selectById(employeeRemove.getEmployeeId());
            employee.setDepartmentId(employeeRemove.getAfterDepartmentId());
            employee.setPositionId(employeeRemove.getAfterPositionId());
            employeeMapper.updateById(employee);
            return RespBean.success("更新成功");
        } catch (Exception e) {
            return RespBean.error("更新失败");
        }
    }
}
