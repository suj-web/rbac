package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.Oplog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface OplogMapper extends BaseMapper<Oplog> {

    /**
     * 查询操作日志
     * @param page
     * @param name
     * @return
     */
    IPage<Oplog> getAllOplogs(Page<Oplog> page, String name);
}
