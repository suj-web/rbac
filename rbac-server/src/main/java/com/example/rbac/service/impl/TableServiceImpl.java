package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rbac.mapper.TableMapper;
import com.example.rbac.pojo.Table;
import com.example.rbac.service.ITableService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-03-17
 */
@Service
public class TableServiceImpl extends ServiceImpl<TableMapper, Table> implements ITableService {

}
