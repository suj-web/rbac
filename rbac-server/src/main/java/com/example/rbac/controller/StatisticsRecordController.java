package com.example.rbac.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.pojo.*;
import com.example.rbac.service.*;
import io.swagger.annotations.ApiOperation;
import javafx.geometry.Pos;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
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

    @Autowired
    private ISalaryAdjustService salaryAdjustService;
    @Autowired
    private IEmployeeRemoveService employeeRemoveService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/position/list")
    public List<Position> getPositions() {
        return positionService.list();
    }

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/department/list")
    public List<Department> getDepartments() {
        return departmentService.list();
    }

    @ApiOperation(value = "人员流动分析表")
    @GetMapping("/")
    public List<RespEmployeeRecordBean> getEmployeeTransaction(String localDate, Integer depId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        if(null == localDate || "".equals(localDate)) {
            localDate = formatter.format(LocalDate.now());
        }
        return employeeService.getEmployeeTransaction(localDate, depId);
    }

    @ApiOperation(value = "导出人员流动分析表")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployeeTransactionTable(String localDate, Integer depId, HttpServletResponse response) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        if(null == localDate || "".equals(localDate)) {
            localDate = formatter.format(LocalDate.now());
        }
        List<RespEmployeeRecordBean> transactionTable = employeeService.getEmployeeTransaction(localDate, depId);
        ExportParams params = new ExportParams(localDate + "月人员流动分析表",localDate + "月人员流动分析表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, RespEmployeeRecordBean.class, transactionTable);
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode(localDate + "月人员流动分析表.xls","UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "员工调薪记录表")
    @GetMapping("/salary/adjust")
    public RespPageBean getSalaryAdjust(@RequestParam(defaultValue = "1") Integer currentPage,
                                              @RequestParam(defaultValue = "10") Integer size,
                                              Integer depId, String localDate) {
        return salaryAdjustService.getAllSalaryAdjust(currentPage, size, depId, localDate);
    }

    @ApiOperation(value = "员工调动记录表")
    @GetMapping("/employee/remove")
    public RespPageBean getEmployeeRemove(@RequestParam(defaultValue = "1") Integer currentPage,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          EmployeeRemove remove, String localDate) {
        return employeeRemoveService.getAllEmployeeRemove(currentPage, size, remove, localDate);
    }
}
