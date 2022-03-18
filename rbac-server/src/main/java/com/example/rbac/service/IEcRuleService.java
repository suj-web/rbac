package com.example.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.EcRule;
import com.example.rbac.pojo.RespPageBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-03-18
 */
public interface IEcRuleService extends IService<EcRule> {

    /**
     * 查询所有奖惩规则
     * @param currentPage
     * @param size
     * @return
     */
    RespPageBean getEcRuleList(Integer currentPage, Integer size);
}
