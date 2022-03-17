package com.example.rbac.task;

import com.example.rbac.pojo.Employee;
import com.example.rbac.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 定时更新员工工龄
 * @Author suj
 * @create 2022/3/17
 */
@Component
@EnableScheduling
public class EmployeeWorkAgeTask {
    @Autowired
    private IEmployeeService employeeService;

    @Scheduled(cron = "0 0 0 1 1 ?")
    public void employeeWorkAgeTask() {
        List<Employee> employees = employeeService.list();
        for(Employee employee: employees) {
            employee.setWorkAge(employee.getWorkAge() + 1);
            employeeService.updateById(employee);
        }
    }

}
