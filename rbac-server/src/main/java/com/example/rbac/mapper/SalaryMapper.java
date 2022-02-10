package com.example.rbac.mapper;

import com.example.rbac.pojo.Salary;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
public interface SalaryMapper extends BaseMapper<Salary> {

    /**
     * 获取所有员工工资信息
     * @param salaryId
     * @param depId
     * @return
     */
    List<Salary> getAllSalary(@Param("salaryId") Integer salaryId, @Param("depId") Integer depId);

}
