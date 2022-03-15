package com.example.rbac.task;

import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.Salary;
import com.example.rbac.pojo.SalaryTable;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.ISalaryService;
import com.example.rbac.service.ISalaryTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @Author suj
 * @create 2022/3/8
 * 每月第一天将员工工资插入工资表
 */
@Component
@EnableScheduling
public class SalaryTableTask {

    @Autowired
    private ISalaryTableService salaryTableService;

    @Autowired
    private IEmployeeService employeeService;

    @Scheduled(cron = "0 0 0 1 * ?")
    public void insert() {
        List<Employee> employees = employeeService.getEmployeeWithSalary2();
        SalaryTable table = new SalaryTable();
        for(Employee employee: employees) {
            table.setEmployeeId(employee.getId());
            table.setYear(LocalDate.now().getYear());
            table.setMonth(LocalDate.now().getMonthValue());
            double salary = employee.getSalary().getBasicSalary() + employee.getSalary().getLunchSalary()
                    + employee.getSalary().getTrafficSalary() + employee.getSalary().getPensionBase()
                    * employee.getSalary().getPensionPer() + employee.getSalary().getMedicalBase()
                    * employee.getSalary().getMedicalPer() + employee.getSalary().getAccumulationFundBase()
                    * employee.getSalary().getAccumulationFundPer();
            table.setAllSalary(salary);
            salaryTableService.save(table);
        }
    }
}
