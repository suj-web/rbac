package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.EmployeeEc;
import com.example.rbac.mapper.EmployeeEcMapper;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IEmployeeEcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
@Service
public class EmployeeEcServiceImpl extends ServiceImpl<EmployeeEcMapper, EmployeeEc> implements IEmployeeEcService {

    @Autowired
    private EmployeeEcMapper employeeEcMapper;

    /**
     * 获取所有奖惩人员信息
     * @param currentPage
     * @param size
     * @param name
     * @param workId
     * @return
     */
    @Override
    public RespPageBean getAllEmployeeEc(Integer currentPage, Integer size, String name, String workId) {
        Page<EmployeeEc> page = new Page<>(currentPage, size);
        IPage<EmployeeEc> employeeEcIPage = employeeEcMapper.getAllEmployeeEc(page, name, workId);
        return new RespPageBean(employeeEcIPage.getTotal(), employeeEcIPage.getRecords());
    }
}
