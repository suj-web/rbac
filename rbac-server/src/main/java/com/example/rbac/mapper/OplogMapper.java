package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.Oplog;
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
public interface OplogMapper extends BaseMapper<Oplog> {

    /**
     * 查询操作日志
     * @param page
     * @param oplog
     * @param operDateScope
     * @return
     */
    IPage<Oplog> getAllOplogs(Page<Oplog> page, @Param("oplog") Oplog oplog, @Param("operDateScope") LocalDate[] operDateScope);
}
