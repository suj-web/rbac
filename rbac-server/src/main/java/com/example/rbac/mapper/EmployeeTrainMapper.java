package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.EmployeeTrain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
@Repository
public interface EmployeeTrainMapper extends BaseMapper<EmployeeTrain> {

    /**
     * 获取员工培训信息
     * @param page
     * @param name
     * @param workId
     * @return
     */
    IPage<EmployeeTrain> getAllEmployeeTrain(Page<EmployeeTrain> page, @Param("name") String name, @Param("workId") String workId);
}
