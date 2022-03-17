package com.example.rbac.service;

import com.example.rbac.pojo.Appraise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespPageBean;

import java.time.LocalDate;

/**
 * <p>
 *  员工考评服务类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
public interface IAppraiseService extends IService<Appraise> {

    /**
     * 获取所有考评信息
     * @param currentSize
     * @param size
     * @param name
     * @param localDate
     * @return
     */
    RespPageBean getAllAppraise(Integer currentSize, Integer size, String name, String localDate);

    /**
     * 考评得分排名
     * @param currentPage
     * @param size
     * @param localDate
     * @param depId
     * @return
     */
    RespPageBean getAppraiseRank(Integer currentPage, Integer size, String localDate, Integer depId);
}
