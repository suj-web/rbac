package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.EmployeeEc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rbac.pojo.RespChartBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
@Repository
public interface EmployeeEcMapper extends BaseMapper<EmployeeEc> {

    /**
     * 获取所有奖惩人员信息
     * @param page
     * @param name
     * @param localDate
     * @param depId
     * @return
     */
    IPage<EmployeeEc> getAllEmployeeEc(Page<EmployeeEc> page, @Param("name") String name, @Param("localDate") String localDate, @Param("depId")Integer depId);

    /**
     * 员工积分统计
     * @param localDate
     * @param depId
     * @return
     */
    List<RespChartBean> getScoreStatistic(@Param("localDate") String localDate, @Param("depId") Integer depId);

    /**
     * 员工积分排名
     * @param page
     * @param localDate
     * @param depId
     * @return
     */
    IPage<EmployeeEc> getScoreRank(Page<EmployeeEc> page, @Param("localDate") String localDate, @Param("depId") Integer depId);

    /**
     * 根据员工id获取员工某个月的总积分
     * @param empId
     * @param localDate
     * @return
     */
    Integer getScoreByEmployeeId(@Param("empId") Integer empId, @Param("localDate") String localDate);
}
