package com.example.rbac.code.generator.service;

import com.example.rbac.code.generator.pojo.RespBean;
import com.example.rbac.code.generator.pojo.TableClass;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author suj
 * @create 2022/4/26
 */
public interface IGenerateCodeService {
    RespBean generateCode(List<TableClass> tableClassList, String realPath);
}
