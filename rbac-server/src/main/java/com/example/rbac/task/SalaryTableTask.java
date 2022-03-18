package com.example.rbac.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.Salary;
import com.example.rbac.pojo.SalaryTable;
import com.example.rbac.service.IEmployeeEcService;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.ISalaryService;
import com.example.rbac.service.ISalaryTableService;
import com.example.rbac.utils.SalaryUtils;
import com.example.rbac.utils.ScoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    /**
     * 每月月初将员工基本工资信息插入到工资表中
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void insert() {
        List<Employee> employees = employeeService.getEmployeeWithSalary2();
        SalaryTable table = new SalaryTable();
        for(Employee employee: employees) {
            table.setEmployeeId(employee.getId());
            table.setYear(LocalDate.now().getYear());
            table.setMonth(LocalDate.now().getMonthValue());
            double salary = SalaryUtils.getSalary(employee.getSalary());
            table.setAllSalary(salary);
            salaryTableService.save(table);
        }
    }

    @Scheduled(cron = "0 0 20 L * ?")
    public void updateBonus() {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        List<SalaryTable> list = salaryTableService.list(new QueryWrapper<SalaryTable>()
                .eq("year", localDate.getYear())
                .eq("month", localDate.getMonthValue()));
        for (SalaryTable salaryTable: list) {
            Integer score = employeeEcService.getScoreByEmployeeId(salaryTable.getEmployeeId(), formatter.format(localDate));
            double bonus = salaryTable.getAllSalary() * 0.1 * ScoreUtils.getScoreGrade(score);
            salaryTable.setBonus(bonus);
            salaryTable.setAllSalary(salaryTable.getAllSalary() + bonus);
            salaryTableService.updateById(salaryTable);
        }
    }
}
