package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.EmployeeTrain;
import com.example.rbac.mapper.EmployeeTrainMapper;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IEmployeeTrainService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
@Service
public class EmployeeTrainServiceImpl extends ServiceImpl<EmployeeTrainMapper, EmployeeTrain> implements IEmployeeTrainService {

    @Autowired
    private EmployeeTrainMapper employeeTrainMapper;

    /**
     * 获取员工培训信息
     * @param currentPage
     * @param size
     * @param name
     * @param localDate
     * @return
     */
    @Override
    public RespPageBean getAllEmployeeTrain(Integer currentPage, Integer size, String name, String localDate) {
        Page<EmployeeTrain> page = new Page<>(currentPage, size);
        IPage<EmployeeTrain> employeeTrainIPage = employeeTrainMapper.getAllEmployeeTrain(page, name, localDate);
        return new RespPageBean(employeeTrainIPage.getTotal(), employeeTrainIPage.getRecords());
    }
}
