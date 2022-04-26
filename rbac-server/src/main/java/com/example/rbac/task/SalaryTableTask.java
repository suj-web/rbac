package com.example.rbac.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.mapper.SalaryMapper;
import com.example.rbac.pojo.Attendance;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.Salary;
import com.example.rbac.pojo.SalaryTable;
import com.example.rbac.service.*;
import com.example.rbac.utils.SalaryUtils;
import com.example.rbac.utils.ScoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

/**
 *
 * @Author suj
 * @create 2022/3/8
 * 定时插入工资表
 */
@Component
@EnableScheduling
public class SalaryTableTask {

    @Autowired
    private ISalaryTableService salaryTableService;

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IEmployeeEcService employeeEcService;
    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private IAttendanceService attendanceService;

    /**
     * 每月月初将员工基本工资信息插入到工资表中
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void insert() {
        List<Employee> employees = employeeService.getEmployeeWithSalary2();
        SalaryTable table = new SalaryTable();
        for(Employee employee: employees) {
            table.setEmployeeId(employee.getId());
            table.setDate(LocalDateTime.now());
            double salary = SalaryUtils.getSalary(employee.getSalary());
            table.setAllSalary(salary);
            salaryTableService.save(table);
        }
    }

    @Scheduled(cron = "0 0 0/2 * * ?")
    public void updateBonus() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        List<SalaryTable> list = salaryTableService.list(new QueryWrapper<SalaryTable>().eq("enabled",1)
                .between("date",date.with(TemporalAdjusters.firstDayOfMonth()),date.with(TemporalAdjusters.lastDayOfMonth())));
        for (SalaryTable salaryTable: list) {
            Integer score = employeeEcService.getScoreByEmployeeId(salaryTable.getEmployeeId(), formatter.format(date));
            Salary salary = salaryService.getById(salaryTable.getSalaryId());
            Double bonus = salary.getBasicSalary() * 0.1 * ScoreUtils.getScoreGrade(score);
            salaryTable.setBonus(bonus);

            Double basicSalary = SalaryUtils.getSalary(salary);
            Integer attendanceDeduction = attendanceService.count(new QueryWrapper<Attendance>().eq("employee_id", salaryTable.getEmployeeId()).eq("absenteeism",1).between("gmt_create", date.with(TemporalAdjusters.firstDayOfMonth()), date.with(TemporalAdjusters.lastDayOfMonth())));
            Integer leaveDeduction = attendanceService.count(new QueryWrapper<Attendance>().eq("employee_id", salaryTable.getEmployeeId()).and(wrapper->wrapper.eq("sick_leave",1).or().eq("personal_leave",1)).between("gmt_create", date.with(TemporalAdjusters.firstDayOfMonth()), date.with(TemporalAdjusters.lastDayOfMonth())));

            Double att = salary.getBasicSalary() / 21.75 * attendanceDeduction;
            salaryTable.setAttendanceDeduction(att);

            Double lea = salary.getBasicSalary() / 21.75 * leaveDeduction;
            salaryTable.setLeaveDeduction(lea);

            Double allSalary = basicSalary + bonus - att - lea;
            salaryTable.setAllSalary(allSalary);
            salaryTableService.updateById(salaryTable);
        }
    }
}
