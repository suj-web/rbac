package com.example.rbac.service.impl;

import com.example.rbac.pojo.Salary;
import com.example.rbac.pojo.SalaryTable;
import com.example.rbac.mapper.SalaryTableMapper;
import com.example.rbac.service.ISalaryTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-02-10
 */
@Service
public class SalaryTableServiceImpl extends ServiceImpl<SalaryTableMapper, SalaryTable> implements ISalaryTableService {

}
