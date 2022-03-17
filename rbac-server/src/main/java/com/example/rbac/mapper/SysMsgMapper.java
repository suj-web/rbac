package com.example.rbac.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.pojo.SysMsg;
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
public interface SysMsgMapper extends BaseMapper<SysMsg> {

    /**
     * 查询系统公告
     * @param page
     * @param sysMsg
     * @return
     */
    IPage<SysMsg> getAllSystemMessage(Page<SysMsg> page, SysMsg sysMsg);
}
