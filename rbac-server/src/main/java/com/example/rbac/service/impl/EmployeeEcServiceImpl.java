package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.EmployeeEc;
import com.example.rbac.mapper.EmployeeEcMapper;
import com.example.rbac.pojo.RespChartBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IEmployeeEcService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class EmployeeEcServiceImpl extends ServiceImpl<EmployeeEcMapper, EmployeeEc> implements IEmployeeEcService {

    @Autowired
    private EmployeeEcMapper employeeEcMapper;

    /**
     * 获取所有奖惩人员信息
     * @param currentPage
     * @param size
     * @param name
     * @param localDate
     * @param depId
     * @return
     */
    @Override
    public RespPageBean getAllEmployeeEc(Integer currentPage, Integer size, String name, String localDate, Integer depId) {
        Page<EmployeeEc> page = new Page<>(currentPage, size);
        IPage<EmployeeEc> employeeEcIPage = employeeEcMapper.getAllEmployeeEc(page, name, localDate, depId);
        return new RespPageBean(employeeEcIPage.getTotal(), employeeEcIPage.getRecords());
    }

    /**
     * 员工积分统计
     * @param localDate
     * @param depId
     * @return
     */
    @Override
    public List<RespChartBean> getScoreStatistic(String localDate, Integer depId) {
        return employeeEcMapper.getScoreStatistic(localDate, depId);
    }

    /**
     * 员工积分排名
     * @param currentPage
     * @param size
     * @param localDate
     * @param depId
     * @return
     */
    @Override
    public RespPageBean getScoreRank(Integer currentPage, Integer size, String localDate, Integer depId) {
        Page<EmployeeEc> page = new Page<>(currentPage, size);
        IPage<EmployeeEc> employeeEcIPage = employeeEcMapper.getScoreRank(page, localDate, depId);
        RespPageBean respPageBean = new RespPageBean(employeeEcIPage.getTotal(), employeeEcIPage.getRecords());
        return respPageBean;
    }

    /**
     * 根据员工id获取员工某个月的总积分
     * @param empId
     * @param localDate
     * @return
     */
    @Override
    public Integer getScoreByEmployeeId(Integer empId, String localDate) {
        return employeeEcMapper.getScoreByEmployeeId(empId, localDate);
    }
}
