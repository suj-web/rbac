package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

import java.time.LocalDate;
import java.util.List;

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
     * @param remove
     * @param localDate
     * @return
     */
    @Override
    public RespPageBean getAllEmployeeRemove(Integer currentSize, Integer size, EmployeeRemove remove, String localDate) {
        Page<EmployeeRemove> page = new Page<>(currentSize, size);
        IPage<EmployeeRemove> employeeRemoveIPage = employeeRemoveMapper.getAllEmployeeRemove(page, remove, localDate);
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
//        List<Employee> list = employeeMapper.list(new QueryWrapper<Employee>().eq("work_id", salaryAdjust.getEmployee().getWorkId()).eq("name", salaryAdjust.getEmployee().getName()));
//        Employee employee = employeeMapper.selectById(employeeRemove.getEmployeeId());
        List<Employee> list = employeeMapper.selectList(new QueryWrapper<Employee>().eq("work_id",employeeRemove.getEmployee().getWorkId()).eq("name",employeeRemove.getEmployee().getName()));
        if(null != list && 1 == list.size() && "离职".equals(list.get(0).getWorkState())){
            return RespBean.error("该员工已离职");
        }
        if(null != list && 1 == list.size()) {
            Employee employee = list.get(0);
            employeeRemove.setEmployeeId(employee.getId());
            if (1 != employeeRemoveMapper.insert(employeeRemove)) {
                return RespBean.error("添加失败");
            }
            try {
                employee.setDepartmentId(employeeRemove.getAfterDepartmentId());
                employee.setPositionId(employeeRemove.getAfterPositionId());
                employeeMapper.updateById(employee);
                return RespBean.success("添加成功");
            } catch (Exception e) {
                return RespBean.error("添加失败");
            }
        }
        return RespBean.error("添加失败");
    }

    @Override
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
