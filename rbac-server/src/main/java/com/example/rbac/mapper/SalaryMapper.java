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
}
