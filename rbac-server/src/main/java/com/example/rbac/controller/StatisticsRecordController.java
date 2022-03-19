package com.example.rbac.controller;

import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.RespEmployeeRecordBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IEmployeeEcService;
import com.example.rbac.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 统计管理-人事记录统计
 * @Author suj
 * @create 2022/1/30
 */
@RestController
@RequestMapping("/statistics/record")
public class StatisticsRecordController {

    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "员工异动信息统计")
    @GetMapping("/")
    public List<RespEmployeeRecordBean> getEmployeeTransaction(String localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        if(null == localDate || "" == localDate) {
            localDate = formatter.format(LocalDate.now());
        }
        return employeeService.getEmployeeTransaction(localDate);
    }
}
