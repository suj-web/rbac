package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rbac.mapper.EcRuleMapper;
import com.example.rbac.pojo.EcRule;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IEcRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-03-18
 */
@Service
public class EcRuleServiceImpl extends ServiceImpl<EcRuleMapper, EcRule> implements IEcRuleService {

    @Autowired
    private EcRuleMapper ecRuleMapper;

    /**
     * 查询所有奖惩规则
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public RespPageBean getEcRuleList(Integer currentPage, Integer size) {
        Page<EcRule> page = new Page<>(currentPage, size);
        IPage<EcRule> ecRulePage = ecRuleMapper.selectPage(page, null);
        RespPageBean pageBean = new RespPageBean(ecRulePage.getTotal(), ecRulePage.getRecords());
        return pageBean;
    }
}
