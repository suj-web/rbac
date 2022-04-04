package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.Appraise;
import com.example.rbac.mapper.AppraiseMapper;
import com.example.rbac.pojo.RespChartBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IAppraiseService;
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
public class AppraiseServiceImpl extends ServiceImpl<AppraiseMapper, Appraise> implements IAppraiseService {


    @Autowired
    private AppraiseMapper appraiseMapper;

    /**
     * 获取所有考评信息
     * @param currentSize
     * @param size
     * @param name
     * @param localDate
     * @return
     */
    @Override
    public RespPageBean getAllAppraise(Integer currentSize, Integer size, String name, String localDate) {
        Page<Appraise> page = new Page<>(currentSize, size);
        IPage<Appraise> appraiseIPage = appraiseMapper.getAllAppraise(page, name, localDate);
        return new RespPageBean(appraiseIPage.getTotal(), appraiseIPage.getRecords());
    }

    /**
     * 考评得分排名
     * @param currentPage
     * @param size
     * @param localDate
     * @param depId
     * @return
     */
    @Override
    public RespPageBean getAppraiseRank(Integer currentPage, Integer size, String localDate, Integer depId) {
        Page<Appraise> page = new Page<>(currentPage, size);
        IPage<Appraise> appraiseIPage = appraiseMapper.getAppraiseRank(page, depId, localDate);
        RespPageBean respPageBean = new RespPageBean(appraiseIPage.getTotal(), appraiseIPage.getRecords());
        return respPageBean;
    }

    /**
     * 部门平均考评得分统计
     * @param localDate
     * @return
     */
    @Override
    public List<RespChartBean> getDepartmentAverageScore(String localDate) {
        return appraiseMapper.getDepartmentAverageScore(localDate);
    }
}
