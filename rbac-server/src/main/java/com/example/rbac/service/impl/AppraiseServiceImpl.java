package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.Appraise;
import com.example.rbac.mapper.AppraiseMapper;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IAppraiseService;
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
}
