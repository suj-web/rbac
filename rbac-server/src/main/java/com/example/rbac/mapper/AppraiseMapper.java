package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.Appraise;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

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
     * @return
     */
    IPage<Appraise> getAllAppraise(Page<Appraise> page, @Param("name") String name, @Param("localDate") String localDate);
}
