package com.example.rbac.service;

import com.example.rbac.pojo.EmployeeEc;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespChartBean;
import com.example.rbac.pojo.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
public interface IEmployeeEcService extends IService<EmployeeEc> {

    /**
     * 获取所有奖惩人员信息
     * @param currentPage
     * @param size
     * @param name
     * @param localDate
     * @param depId
     * @return
     */
    RespPageBean getAllEmployeeEc(Integer currentPage, Integer size, String name, String localDate, Integer depId);

    /**
     * 员工积分统计
     * @param localDate
     * @param depId
     * @return
     */
    List<RespChartBean> getScoreStatistic(String localDate, Integer depId);

    /**
     * 员工积分排名
     * @param currentPage
     * @param size
     * @param localDate
     * @param depId
     * @return
     */
    RespPageBean getScoreRank(Integer currentPage, Integer size, String localDate, Integer depId);

    /**
     * 根据员工id获取员工某个月的总积分
     * @param empId
     * @param localDate
     * @return
     */
    Integer getScoreByEmployeeId(Integer empId, String localDate);
}
