package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.EmployeeData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author suj
 * @since 2022-04-25
 */
@Repository
public interface EmployeeDataMapper extends BaseMapper<EmployeeData> {

    /**
     * 查询员工高级资料
     * @param page
     * @param depId
     * @param name
     * @return
     */
    IPage<EmployeeData> getAllEmpData(Page<EmployeeData> page, @Param("depId") Integer depId, @Param("name") String name);
}
