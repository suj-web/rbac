package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.EmployeeRemove;
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
public interface EmployeeRemoveMapper extends BaseMapper<EmployeeRemove> {

    /**
     * 获取所有员工调动资料
     * @param page
     * @param name
     * @param workId
     * @return
     */
    IPage<EmployeeRemove> getAllEmployeeRemove(Page<EmployeeRemove> page, @Param("name") String name, @Param("workId") String workId);
}
