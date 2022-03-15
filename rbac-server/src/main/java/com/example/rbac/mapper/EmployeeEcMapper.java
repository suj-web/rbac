package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.EmployeeEc;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

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
     * @return
     */
    IPage<EmployeeEc> getAllEmployeeEc(Page<EmployeeEc> page, @Param("name") String name, @Param("localDate") String localDate);
}
