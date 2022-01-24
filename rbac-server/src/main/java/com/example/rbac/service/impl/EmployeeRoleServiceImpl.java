package com.example.rbac.service.impl;

import com.example.rbac.pojo.EmployeeRole;
import com.example.rbac.mapper.EmployeeRoleMapper;
import com.example.rbac.service.IEmployeeRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-01-20
 */
@Service
public class EmployeeRoleServiceImpl extends ServiceImpl<EmployeeRoleMapper, EmployeeRole> implements IEmployeeRoleService {

}
