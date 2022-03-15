package com.example.rbac.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.ISalaryTableService;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDate;
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
    private IEmployeeService employeeService;

    @OperationLogAnnotation(operModul = "人事管理-月末处理",operType = "查询",operDesc = "获取所有员工当月工资信息")
    @ApiOperation(value = "获取所有员工当月工资信息")
    @GetMapping("/")
    public RespPageBean getAllEmployeeWithSalaryTable(@RequestParam(defaultValue = "1") Integer currentPage,
                                                      @RequestParam(defaultValue = "10") Integer size,
                                                      Integer depId) {
        return employeeService.getAllEmployeeWithSalaryTable(currentPage, size, depId);
    }

    @OperationLogAnnotation(operModul = "人事管理-月末处理",operType = "更新",operDesc = "锁定当月账单")
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

    @OperationLogAnnotation(operModul = "人事管理-月末处理",operType = "更新",operDesc = "解锁当月账单")
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
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        List<Employee> list = employeeService.getAllEmployeeWithSalaryTable2(year, month);
        List<ExportSalaryTable> salaryTables = new ArrayList<>();
        for(Employee item: list) {
            ExportSalaryTable table = new ExportSalaryTable();
            table.setWorkId(item.getWorkId());
            table.setName(item.getName());
            table.setDepName(item.getDepartment().getName());
            table.setYear(item.getSalaryTables().get(0).getYear());
            table.setMonth(item.getSalaryTables().get(0).getMonth());
            table.setBonus(item.getSalaryTables().get(0).getBonus());
            table.setAllSalary(item.getSalaryTables().get(0).getAllSalary());
            salaryTables.add(table);
        }

        ExportParams params = new ExportParams(year+"年"+month+"月账单",year+"年"+month+"月账单", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, ExportSalaryTable.class, salaryTables);
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode(year+"年"+month+"月账单表.xls","UTF-8"));
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
