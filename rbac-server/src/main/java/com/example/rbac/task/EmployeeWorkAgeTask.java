package com.example.rbac.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.pojo.Employee;
import com.example.rbac.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");

    @Scheduled(cron = "0 0 20 * * ?")
    public void employeeWorkAgeTask() {
        LocalDate localDate = LocalDate.now();
        List<Employee> employees = employeeService.list(new QueryWrapper<Employee>().eq("work_state","在职"));
        for(Employee employee: employees) {
            if(localDate.getYear() > employee.getBeginDate().getYear()) {
                if(formatter.format(localDate).compareTo(formatter.format(employee.getBeginDate())) > 0) {
                    if(null == employee.getWorkAge()) {
                        employee.setWorkAge(0);
                    }
                    employee.setWorkAge(employee.getWorkAge() + 1);
                    employeeService.updateById(employee);
                }
            }
        }
    }

}
