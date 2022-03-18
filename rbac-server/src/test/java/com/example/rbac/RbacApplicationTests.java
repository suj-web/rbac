package com.example.rbac;

import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.SalaryTable;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.ISalaryTableService;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@SpringBootTest
class RbacApplicationTests {

    @Test
    void contextLoads() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("admin"));
    }

    @Test
    public void test1() {
        String localDate = "2022-03";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//        LocalDate date = LocalDate.parse(localDate, formatter).with(TemporalAdjusters.firstDayOfMonth());
//        System.out.println(formatter1.format(LocalDate.now()));
//        System.out.println();
//        TemporalAccessor parse = formatter1.parse("2022-03");
        LocalDate parse1 = LocalDate.from(formatter1.parse("2022-01-02")).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate parse2 = LocalDate.from(formatter1.parse("2022-01-02")).with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(parse1);
        System.out.println(parse2);
//        LocalDate.
//        LocalDate.from()
//        System.out.println(from);
    }

    @Autowired
    private ISalaryTableService salaryTableService;

    @Autowired
    private IEmployeeService employeeService;

    @Test
    public void test(){
        List<Employee> employees = employeeService.list();
        for(Employee employee: employees) {
            employee.setWorkAge(LocalDate.now().getYear() - employee.getBeginDate().getYear());
            employeeService.updateById(employee);
        }
    }

    @Test
    public void insert(){
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
