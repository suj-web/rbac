package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.pojo.SalaryAdjust;
import com.example.rbac.mapper.SalaryAdjustMapper;
import com.example.rbac.service.ISalaryAdjustService;
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
 * @since 2022-01-12
 */
@Service
public class SalaryAdjustServiceImpl extends ServiceImpl<SalaryAdjustMapper, SalaryAdjust> implements ISalaryAdjustService {

    @Autowired
    private SalaryAdjustMapper salaryAdjustMapper;

    /**
     * 获取所有员工调薪信息
     * @param currentPage
     * @param size
     * @param depId
     * @param localDate
     * @return
     */
    @Override
    public RespPageBean getAllSalaryAdjust(Integer currentPage, Integer size, Integer depId, String localDate) {
        Page<SalaryAdjust> page = new Page<>(currentPage, size);
        IPage<SalaryAdjust> salaryAdjustIPage = salaryAdjustMapper.getAllSalaryAdjust(page, depId, localDate);
        return new RespPageBean(salaryAdjustIPage.getTotal(), salaryAdjustIPage.getRecords());
    }
}
