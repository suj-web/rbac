package com.example.rbac.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.Oplog;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.RespPageBean;
import com.example.rbac.service.IOplogService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * 系统管理-操作日志管理
 * @author suj
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/system/log")
public class OplogController {

    @Autowired
    private IOplogService oplogService;

    @OperationLogAnnotation(operModul = "操作日志", operType = "查询", operDesc = "查询操作日志")
    @ApiOperation(value = "查询操作日志")
    @GetMapping("/")
    public RespPageBean getAllOplogs(@RequestParam(defaultValue = "1") Integer currentPage,
                                     @RequestParam(defaultValue = "10") Integer size,
                                     Oplog oplog, LocalDate[] operDateScope) {
        return oplogService.getAllOplogs(currentPage, size, oplog, operDateScope);
    }

    @OperationLogAnnotation(operModul = "操作日志", operType = "删除", operDesc = "批量删除操作日志")
    @ApiOperation(value = "批量删除操作日志")
    @DeleteMapping("/{id}")
    public RespBean deleteByIds(@PathVariable Integer id) {
        if(oplogService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "操作日志", operType = "删除", operDesc = "批量删除操作日志")
    @ApiOperation(value = "批量删除操作日志")
    @DeleteMapping("/")
    public RespBean deleteByIds(Integer[] ids) {
        if(oplogService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "操作日志",operType = "导出",operDesc = "导出操作日志")
    @ApiOperation(value = "导出操作日志")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response){
        List<Oplog> list = oplogService.list(new QueryWrapper<Oplog>().eq("is_delete", false));
        ExportParams params = new ExportParams("操作日志表","操作日志表", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, Oplog.class, list);
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("操作日志表.xls","UTF-8"));
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(null!=outputStream) {
                try {
                    outputStream.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
