package com.example.rbac.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.ISalaryTableService;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 人事管理-月末处理
 * @Author suj
 * @create 2022/1/30
 */
@RestController
@RequestMapping("/salary/month")
public class SalaryMonthController {

    @Autowired
    private ISalaryTableService salaryTableService;
    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "获取所有部门信息")
    @GetMapping("/department")
    public List<Department> getAllDepartment() {
        return departmentService.list();
    }

    @OperationLogAnnotation(operModul = "月末处理",operType = "查询",operDesc = "获取所有员工当月工资信息")
    @ApiOperation(value = "获取所有员工当月工资信息")
    @GetMapping("/")
    public RespPageBean getAllEmployeeWithSalaryTable(@RequestParam(defaultValue = "1") Integer currentPage,
                                                      @RequestParam(defaultValue = "10") Integer size,
                                                      Integer depId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDateTime localDate = LocalDateTime.now();
        return salaryTableService.getAllSalaryTableByCurrentMonth(currentPage, size, depId, formatter.format(localDate));
    }

    @OperationLogAnnotation(operModul = "月末处理",operType = "更新",operDesc = "锁定当月账单")
    @ApiOperation(value = "锁定当月账单")
    @PutMapping("/lock")
    public RespBean lockSalaryTable(Integer[] ids) {
        List<SalaryTable> tables = salaryTableService.listByIds(Arrays.asList(ids));
        try {
            for (SalaryTable table : tables) {
                table.setEnabled(true);
                salaryTableService.updateById(table);
            }
            return RespBean.success("操作成功");
        } catch (Exception e) {
            return RespBean.error("操作失败");
        }
    }

    @OperationLogAnnotation(operModul = "月末处理",operType = "更新",operDesc = "解锁当月账单")
    @ApiOperation(value = "解锁当月账单")
    @PutMapping("/unlock")
    public RespBean unlockSalaryTable(Integer[] ids) {
        List<SalaryTable> tables = salaryTableService.listByIds(Arrays.asList(ids));
        try {
            for (SalaryTable table : tables) {
                table.setEnabled(false);
                salaryTableService.updateById(table);
            }
            return RespBean.success("操作成功");
        } catch (Exception e) {
            return RespBean.error("操作失败");
        }
    }

    //导出当月账单
    @ApiOperation(value = "导出当月账单")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportSalaryTable(HttpServletResponse response) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDateTime localDate = LocalDateTime.now();
        List<SalaryTable> list = salaryTableService.getAllSalaryTablesForExcel(formatter.format(localDate));
        List<SalaryTableExcel> salaryTables = new ArrayList<>();
        for(SalaryTable item: list) {
            SalaryTableExcel table = new SalaryTableExcel();
            table.setWorkId(item.getEmployee().getWorkId());
            table.setName(item.getEmployee().getName());
            table.setDepName(item.getEmployee().getDepartment().getName());
            table.setPosition(item.getEmployee().getPosition().getName());
            table.setDate(item.getDate());
            table.setBonus(item.getBonus());
            table.setAllSalary(item.getAllSalary());
            salaryTables.add(table);
        }

        ExportParams params = new ExportParams(localDate.getYear()+"年"+localDate.getMonthValue()+"月账单",localDate.getYear()+"年"+localDate.getMonthValue()+"月账单", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, SalaryTableExcel.class, salaryTables);
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode(localDate.getYear()+"年"+localDate.getMonthValue()+"月账单表.xls","UTF-8"));
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
}
