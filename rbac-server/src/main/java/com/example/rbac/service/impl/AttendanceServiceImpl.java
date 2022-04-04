package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.mapper.DepartmentMapper;
import com.example.rbac.mapper.EmployeeMapper;
import com.example.rbac.mapper.PositionMapper;
import com.example.rbac.pojo.*;
import com.example.rbac.mapper.AttendanceMapper;
import com.example.rbac.service.IAttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * 员工考勤
 * @author suj
 * @since 2022-03-08
 */
@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements IAttendanceService {

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DepartmentMapper departmentMapper;
    @Autowired
    private PositionMapper positionMapper;
    /**
     * 考勤信息统计
     * @param currentPage
     * @param size
     * @param localDate
     * @return
     */
    @Override
    public RespPageBean getAllAttendance(Integer currentPage, Integer size, LocalDate localDate, Integer depId) {
        Integer count = attendanceMapper.selectList(new QueryWrapper<Attendance>().between("gmt_create", localDate.with(TemporalAdjusters.firstDayOfMonth()), localDate.with(TemporalAdjusters.lastDayOfMonth()))).size();
        List<RespAttendanceTable> attendanceTables = new ArrayList<>();
        if(null == count || 0 == count) {
            return new RespPageBean(0L, attendanceTables);
        }
        Page<Employee> page = new Page<>(currentPage, size);
        LocalDateTime workAttendanceTime = attendanceMapper.selectList(null).get((0)).getWorkAttendanceTime();
        LocalDateTime offDutyAttendanceTime = attendanceMapper.selectList(null).get(0).getOffDutyAttendanceTime();

        IPage<Employee> emps = null;
        if(null != depId) {
           emps = employeeMapper.selectPage(page, new QueryWrapper<Employee>().eq("department_id",depId).eq("work_state", "在职"));
        } else {
            emps = employeeMapper.selectPage(page, new QueryWrapper<Employee>().eq("work_state", "在职"));
        }
        for(Employee emp: emps.getRecords()) {
            RespAttendanceTable table = new RespAttendanceTable();
            table.setWorkId(emp.getWorkId());
            table.setName(emp.getName());
            table.setDepName(departmentMapper.selectById(emp.getDepartmentId()).getName());
            table.setPosName(positionMapper.selectById(emp.getPositionId()).getName());
            //上班考勤时间
            table.setWorkAttendanceTime(workAttendanceTime);
            //下班考勤时间
            table.setOffDutyAttendanceTime(offDutyAttendanceTime);
            //事假(天)
            table.setPersonalLeave(attendanceMapper.selectCount(new QueryWrapper<Attendance>().eq("employee_id",emp.getId()).eq("personal_leave",1).between("gmt_create",localDate.with(TemporalAdjusters.firstDayOfMonth()),localDate.with(TemporalAdjusters.lastDayOfMonth()))));
            //病假(天)
            table.setSickLeave(attendanceMapper.selectCount(new QueryWrapper<Attendance>().eq("employee_id",emp.getId()).eq("sick_leave",1).between("gmt_create",localDate.with(TemporalAdjusters.firstDayOfMonth()),localDate.with(TemporalAdjusters.lastDayOfMonth()))));
            //应出勤小时数
            table.setRequiredAttendanceHours((offDutyAttendanceTime.toEpochSecond(ZoneOffset.of("+8")) - workAttendanceTime.toEpochSecond(ZoneOffset.of("+8"))) / 60.0 / 60.0 * 21.75);

            //实出勤小时数
            Double actualHours = 0.0;
            List<Attendance> attendances = attendanceMapper.selectList(new QueryWrapper<Attendance>().eq("employee_id", emp.getId()).isNotNull("punch_in_time").isNotNull("punch_out_time").eq("absenteeism",false).between("gmt_create", localDate.with(TemporalAdjusters.firstDayOfMonth()), localDate.with(TemporalAdjusters.lastDayOfMonth())));
            for(Attendance att: attendances) {
                if(att.getPersonalLeave()==1 || att.getSickLeave()==1) {
                    actualHours += 0.0;
                }else {
                    Double endTime = att.getPunchOutTime().toEpochSecond(ZoneOffset.of("+8")) > offDutyAttendanceTime.toEpochSecond(ZoneOffset.of("+8")) ?
                            offDutyAttendanceTime.toEpochSecond(ZoneOffset.of("+8")) / 3600.0 : att.getPunchOutTime().toEpochSecond(ZoneOffset.of("+8")) / 3600.0;
                    Double startTime = att.getPunchInTime().toEpochSecond(ZoneOffset.of("+8")) > workAttendanceTime.toEpochSecond(ZoneOffset.of("+8")) ?
                            att.getPunchInTime().toEpochSecond(ZoneOffset.of("+8")) / 3600.0 : workAttendanceTime.toEpochSecond(ZoneOffset.of("+8")) / 3600.0;
                    actualHours = actualHours + endTime - startTime;
                }
            }
            table.setActualAttendanceHours(actualHours);

            //迟到次数
            table.setLateTimes(attendanceMapper.selectCount(new QueryWrapper<Attendance>().eq("employee_id", emp.getId()).isNotNull("punch_in_time").eq("absenteeism",false).gt("punch_in_time",workAttendanceTime).between("gmt_create", localDate.with(TemporalAdjusters.firstDayOfMonth()), localDate.with(TemporalAdjusters.lastDayOfMonth()))));
            //迟到分钟数
            List<Attendance> lateList = attendanceMapper.selectList(new QueryWrapper<Attendance>().eq("employee_id", emp.getId()).isNotNull("punch_in_time").eq("absenteeism",false).gt("punch_in_time", workAttendanceTime).between("gmt_create", localDate.with(TemporalAdjusters.firstDayOfMonth()), localDate.with(TemporalAdjusters.lastDayOfMonth())));
            Double lateMinutes = 0.0;
            for(Attendance att: lateList) {
                lateMinutes = lateMinutes + att.getPunchInTime().toEpochSecond(ZoneOffset.of("+8")) / 60.0 - workAttendanceTime.toEpochSecond(ZoneOffset.of("+8")) / 60.0;
            }
            table.setLateMinutes(lateMinutes);
            //早退次数
            table.setLeaveEarlyTimes(attendanceMapper.selectCount(new QueryWrapper<Attendance>().eq("employee_id", emp.getId()).isNotNull("punch_out_time").eq("absenteeism",false).lt("punch_out_time",offDutyAttendanceTime).between("gmt_create", localDate.with(TemporalAdjusters.firstDayOfMonth()), localDate.with(TemporalAdjusters.lastDayOfMonth()))));
            //早退分钟数
            List<Attendance> leaveEarlyList = attendanceMapper.selectList(new QueryWrapper<Attendance>().eq("employee_id", emp.getId()).isNotNull("punch_out_time").eq("absenteeism",false).lt("punch_out_time",offDutyAttendanceTime).between("gmt_create", localDate.with(TemporalAdjusters.firstDayOfMonth()), localDate.with(TemporalAdjusters.lastDayOfMonth())));
            Double leaveEarlyMinutes = 0.0;
            for(Attendance att: leaveEarlyList) {
                leaveEarlyMinutes = leaveEarlyMinutes + offDutyAttendanceTime.toEpochSecond(ZoneOffset.of("+8")) / 60.0  - att.getPunchOutTime().toEpochSecond(ZoneOffset.of("+8")) / 60.0;
            }
            table.setLeaveEarlyMinutes(leaveEarlyMinutes);
           //缺勤小时数
            Integer absenteeismDays = attendanceMapper.selectCount(new QueryWrapper<Attendance>().eq("employee_id", emp.getId()).eq("absenteeism",true).between("gmt_create", localDate.with(TemporalAdjusters.firstDayOfMonth()), localDate.with(TemporalAdjusters.lastDayOfMonth())));

            table.setAbsenteeismHours(Duration.between(workAttendanceTime, offDutyAttendanceTime).toMillis() / 1000.0 / 3600.0 * absenteeismDays * 1.0);
            //缺勤次数
            table.setAbsenteeismTimes(absenteeismDays);

            attendanceTables.add(table);
        }
//        IPage<Attendance> attendanceIPage = attendanceMapper.getAllAttendance(page, localDate, depId);
        RespPageBean respPageBean = new RespPageBean(emps.getTotal(), attendanceTables);
        return respPageBean;
    }

    /**
     * 人事管理-员工考勤-查询员工考勤信息
     * @param currentPage
     * @param size
     * @param depId
     * @param beginDateScope
     * @return
     */
    @Override
    public RespPageBean getAttendances(Integer currentPage, Integer size, Integer depId, LocalDate[] beginDateScope) {
        Page<Attendance> page = new Page<>(currentPage, size);
        IPage<Attendance> attendanceIPage = attendanceMapper.getAttendances(page, depId, beginDateScope);
        RespPageBean respPageBean = new RespPageBean(attendanceIPage.getTotal(), attendanceIPage.getRecords());
        return respPageBean;
    }

    @Override
    public List<Attendance> getAttendanceForExcel(LocalDate[] dateScope) {
        return attendanceMapper.getAttendanceForExcel(dateScope);
    }
}
