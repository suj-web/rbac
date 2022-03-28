package com.example.rbac.controller;


import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IAppraiseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 *人事管理-员工考评
 * @author suj
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/personnel/appraise")
public class AppraiseController {

    @Autowired
    private IAppraiseService appraiseService;

    @OperationLogAnnotation(operModul = "员工考评",operType = "查询",operDesc = "查询员工考评信息")
    @ApiOperation(value = "查询员工考评信息")
    @GetMapping("/")
    public RespPageBean getAllAppraises(@RequestParam(defaultValue = "1") Integer currentPage,
                                        @RequestParam(defaultValue = "10") Integer size,
                                        String name, String localDate) {
        return appraiseService.getAllAppraise(currentPage, size, name, localDate);
    }
}
