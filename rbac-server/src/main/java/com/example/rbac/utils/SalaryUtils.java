package com.example.rbac.utils;

import com.example.rbac.pojo.Salary;

/**
 * @Author suj
 * @create 2022/3/18
 */
public class SalaryUtils {
    /**
     * 根据工资账套获取基本工资
     * @param salary
     * @return
     */
    public static double getSalary(Salary salary) {
        double baseSalary = salary.getBasicSalary() + salary.getLunchSalary()
                + salary.getTrafficSalary() + salary.getPensionBase()
                * salary.getPensionPer() + salary.getMedicalBase()
                * salary.getMedicalPer() + salary.getAccumulationFundBase()
                * salary.getAccumulationFundPer();
        return baseSalary;
    }
}
