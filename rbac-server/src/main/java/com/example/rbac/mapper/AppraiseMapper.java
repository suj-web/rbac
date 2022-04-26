package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.Appraise;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.rbac.pojo.RespChartBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  员工考评Mapper 接口
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
@Repository
public interface AppraiseMapper extends BaseMapper<Appraise> {

    /**
     * 获取所有考评信息
     * @param page
     * @param name
     * @param localDate
     * @param depId
     * @return
     */
    IPage<Appraise> getAllAppraise(Page<Appraise> page, @Param("name") String name, @Param("localDate") String localDate, @Param("depId") Integer depId);

    /**
     * 考评得分排名
     * @param page
     * @param depId
     * @param localDate
     * @return
     */
    IPage<Appraise> getAppraiseRank(Page<Appraise> page, @Param("depId") Integer depId, @Param("localDate") String localDate);

    /**
     * 部门平均考评得分统计
     * @param localDate
     * @return
     */
    List<RespChartBean> getDepartmentAverageScore(@Param("localDate") String localDate);
}
