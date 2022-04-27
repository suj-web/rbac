package com.example.rbac.code.generator.controller;

import com.example.rbac.code.generator.pojo.RespBean;
import com.example.rbac.code.generator.pojo.TableClass;
import com.example.rbac.code.generator.service.IGenerateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author suj
 * @create 2022/4/26
 */
@RestController
public class GenerateCodeController {
    @Autowired
    private IGenerateCodeService generateCodeService;

    @PostMapping("/generateCode")
    public RespBean generateCode(@RequestBody List<TableClass> tableClassList, HttpServletRequest request) {
        return generateCodeService.generateCode(tableClassList, request.getServletContext().getRealPath("/"));
    }
}
