package com.example.rbac.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IAttendanceService;
import com.example.rbac.service.IDepartmentService;
import com.example.rbac.service.IEmployeeService;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.aspectj.lang.annotation.DeclareWarning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
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
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "查询所有部门")
    @GetMapping("/department/list")
    public List<Department> getAllDepartments() {
        return departmentService.list();
    }

    @OperationLogAnnotation(operModul = "员工考勤",operType = "查询",operDesc = "查询员工考勤信息")
    @ApiOperation(value = "查询员工考勤信息")
    @GetMapping("/list")
    public RespPageBean getAttendances(@RequestParam(defaultValue = "1") Integer currentPage,
                                       @RequestParam(defaultValue = "10") Integer size,
                                       Integer depId, LocalDate[] beginDateScope) {
        return attendanceService.getAttendances(currentPage, size, depId, beginDateScope);
    }

    @OperationLogAnnotation(operModul = "员工考勤",operType = "添加",operDesc = "添加员工考勤信息")
    @ApiOperation(value = "添加员工考勤信息")
    @PostMapping("/")
    public RespBean addEcRule(@RequestBody AttendanceParam param) {
        List<Employee> emps = employeeService.list(new QueryWrapper<Employee>().eq("work_id", param.getWorkId()).eq("work_state","在职"));
        if(null == emps) {
            return RespBean.error("该员工不存在");
        } else {
            if(!emps.get(0).getName().equals(param.getName())) {
                return RespBean.error("员工工号和姓名不匹配");
            }
        }
        Attendance attendance = new Attendance();
        LocalDate localDate = LocalDate.now();
        attendance.setEmployeeId(emps.get(0).getId());
        attendance.setWorkAttendanceTime(LocalDateTime.of(localDate.getYear(),localDate.getMonthValue(),localDate.getDayOfMonth(),8,0,0));
        attendance.setOffDutyAttendanceTime(LocalDateTime.of(localDate.getYear(),localDate.getMonthValue(),localDate.getDayOfMonth(),18,0,0));
        attendance.setPunchInTime(param.getPunchInTime());
        attendance.setPunchOutTime(param.getPunchOutTime());
        attendance.setPersonalLeave(param.getPersonalLeave());
        attendance.setSickLeave(param.getSickLeave());
        attendance.setAbsenteeism(param.getAbsenteeism());
        if(attendanceService.save(attendance)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @OperationLogAnnotation(operModul = "员工考勤",operType = "修改",operDesc = "修改员工考勤信息")
    @ApiOperation(value = "修改员工考勤信息")
    @PutMapping("/")
    public RespBean updateEcRule(@RequestBody AttendanceParam param) {
        List<Employee> emps = employeeService.list(new QueryWrapper<Employee>().eq("work_id", param.getWorkId()).eq("work_state","在职"));
        if(null == emps) {
            return RespBean.error("该员工不存在");
        } else {
            if(!emps.get(0).getName().equals(param.getName())) {
                return RespBean.error("员工工号和姓名不匹配");
            }
        }
        Attendance attendance = new Attendance();
        attendance.setId(param.getId());
        attendance.setEmployeeId(emps.get(0).getId());
        attendance.setPunchInTime(param.getPunchInTime());
        attendance.setPunchOutTime(param.getPunchOutTime());
        attendance.setPersonalLeave(param.getPersonalLeave());
        attendance.setSickLeave(param.getSickLeave());
        attendance.setAbsenteeism(param.getAbsenteeism());
        if(attendanceService.updateById(attendance)) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @OperationLogAnnotation(operModul = "员工考勤",operType = "删除",operDesc = "删除员工考勤信息")
    @ApiOperation(value = "删除员工考勤信息")
    @DeleteMapping("/{id}")
    public RespBean deleteEcRuleById(@PathVariable Integer id) {
        if (attendanceService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "员工考勤",operType = "删除",operDesc = "批量删除员工考勤信息")
    @ApiOperation(value = "批量删除员工考勤信息")
    @DeleteMapping("/")
    public RespBean deleteEcRuleByIds(Integer[] ids) {
        if (attendanceService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    //导出打卡记录
    @OperationLogAnnotation(operModul = "考勤管理",operType = "导出",operDesc = "导出打卡记录")
    @ApiOperation(value = "导出打卡记录")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportSalaryTable(HttpServletResponse response, LocalDate[] dateScope) {
        if(null == dateScope || 0 == dateScope.length) {
            dateScope = new LocalDate[2];
            dateScope[0] = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            dateScope[1] = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        }
        List<Attendance> list = attendanceService.getAttendanceForExcel(dateScope);
        List<AttendanceExcel> attendances = new ArrayList<>();
        for(Attendance attendance: list) {
            AttendanceExcel excel = new AttendanceExcel();
            excel.setWorkId(attendance.getEmployee().getWorkId());
            excel.setName(attendance.getEmployee().getName());
            excel.setDepName(attendance.getEmployee().getDepartment().getName());
            excel.setPosition(attendance.getEmployee().getPosition().getName());
            excel.setAttendanceTime(attendance.getPunchInTime());
            excel.setClosingTime(attendance.getPunchOutTime());
            excel.setAbsenteeism(attendance.getAbsenteeism());
            excel.setPersonalLeave(attendance.getPersonalLeave());
            excel.setSickLeave(attendance.getSickLeave());
            excel.setDate(attendance.getGmtCreate());

            attendances.add(excel);
        }

        ExportParams params = new ExportParams(dateScope[0].getMonthValue()+"月打卡记录",dateScope[0].getMonthValue()+"月打卡记录", ExcelType.HSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, AttendanceExcel.class, attendances);
        ServletOutputStream outputStream = null;
        try {
            response.setHeader("content-type","application/octet-stream");
            response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode(dateScope[0].getMonthValue()+"月打卡记录.xls","UTF-8"));
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

    @OperationLogAnnotation(operModul = "考勤管理",operType = "导入",operDesc = "导入打卡记录")
    @ApiOperation(value = "导入打卡记录")
    @PostMapping("/import")
    public RespBean importEmployee(MultipartFile file) {
        ImportParams params = new ImportParams();
        //去掉标题行
        params.setTitleRows(1);
        List<Attendance> attendances = new ArrayList<>();
        List<Employee> employees = employeeService.list(new QueryWrapper<Employee>().eq("work_state", "在职"));
        try {
            List<AttendanceExcel> list = ExcelImportUtil.importExcel(file.getInputStream(), AttendanceExcel.class, params);
            for(AttendanceExcel item: list) {
                Attendance attendance = new Attendance();
                for(Employee emp: employees) {
                    if(item.getWorkId().equals(emp.getWorkId())) {
                        attendance.setEmployeeId(emp.getId());
                        break;
                    }
                }
                attendance.setPunchInTime(item.getAttendanceTime());
                attendance.setPunchOutTime(item.getClosingTime());
                attendance.setPersonalLeave(item.getPersonalLeave());
                attendance.setSickLeave(item.getSickLeave());
                attendance.setAbsenteeism(item.getAbsenteeism());

                attendance.setWorkAttendanceTime(LocalDateTime.of(item.getDate().getYear(),item.getDate().getMonthValue(),item.getDate().getDayOfMonth(), 8,0,0));
                attendance.setOffDutyAttendanceTime(LocalDateTime.of(item.getDate().getYear(),item.getDate().getMonthValue(),item.getDate().getDayOfMonth(), 18,0,0));
                attendances.add(attendance);
            }
            if(attendanceService.saveBatch(attendances)) {
                return RespBean.success("导入成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入失败");
    }
}
