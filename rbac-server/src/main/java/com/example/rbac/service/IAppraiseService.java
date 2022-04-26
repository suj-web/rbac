package com.example.rbac.service;

import com.example.rbac.pojo.Appraise;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespChartBean;
import com.example.rbac.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

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
     * @param depId
     * @return
     */
    RespPageBean getAllAppraise(Integer currentSize, Integer size, String name, String localDate, Integer depId);

    /**
     * 考评得分排名
     * @param currentPage
     * @param size
     * @param localDate
     * @param depId
     * @return
     */
    RespPageBean getAppraiseRank(Integer currentPage, Integer size, String localDate, Integer depId);

    /**
     * 部门平均考评得分统计
     * @param localDate
     * @return
     */
    List<RespChartBean> getDepartmentAverageScore(String localDate);
}
