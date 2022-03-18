package com.example.rbac.controller;


import com.example.rbac.pojo.*;
import com.example.rbac.service.IAttendanceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 *人事管理-员工考勤
 * @author suj
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/salary/attendance")
public class AttendanceController {

    @Autowired
    private IAttendanceService attendanceService;

    @ApiOperation(value = "查询员工考勤信息")
    @GetMapping("/list")
    public RespPageBean getAttendances(@RequestParam(defaultValue = "1") Integer currentPage,
                                         @RequestParam(defaultValue = "10") Integer size,
                                         LocalDate[] beginDateScope) {
        return attendanceService.getAttendances(currentPage, size, beginDateScope);
    }

    @ApiOperation(value = "添加员工考勤信息")
    @PostMapping("/")
    public RespBean addEcRule(@RequestBody Attendance attendance) {
        if(attendanceService.save(attendance)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "修改员工考勤信息")
    @PutMapping("/")
    public RespBean updateEcRule(@RequestBody Attendance attendance) {
        if(attendanceService.updateById(attendance)) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation(value = "删除员工考勤信息")
    @DeleteMapping("/{id}")
    public RespBean deleteEcRuleById(@PathVariable Integer id) {
        if (attendanceService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除员工考勤信息")
    @DeleteMapping("/")
    public RespBean deleteEcRuleByIds(Integer[] ids) {
        if (attendanceService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
