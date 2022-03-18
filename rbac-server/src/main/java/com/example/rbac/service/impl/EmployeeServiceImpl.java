package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.mapper.MailLogMapper;
import com.example.rbac.mapper.RoleMapper;
import com.example.rbac.pojo.*;
import com.example.rbac.mapper.EmployeeMapper;
import com.example.rbac.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.rbac.utils.UserUtils;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailLogMapper mailLogMapper;


    /**
     * 根据username获取员工实体
     * @param username
     * @return
     */
    @Override
    public Employee getEmployeeByUserName(String username) {
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("username",username));
        return employee;
    }

    /**
     * 分页查询员工信息
     * @param currentPage
     * @param size
     * @param employee
     * @param beginDateScope
     * @return
     */
    @Override
    public RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeeByPage = employeeMapper.getEmployeeByPage(page, employee, beginDateScope);
        RespPageBean respPageBean = new RespPageBean(employeeByPage.getTotal(), employeeByPage.getRecords());
        return respPageBean;
    }

    @Override
    public RespBean maxWorkID() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(work_id)"));
        String maxWorkId = String.format("%08d",Integer.parseInt(maps.get(0).get("max(work_id)").toString()) + 1);
        return RespBean.success(null, maxWorkId);
    }

    /**
     * 添加员工
     * @param employee
     * @return
     */
    @Override
    @Transactional
    public RespBean addEmployee(Employee employee) {
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days / 365.0)));

        if(1 == employeeMapper.insert(employee)){
            //发送信息
            Employee emp = employeeMapper.getEmployee(employee.getId()).get(0);

            //数据库记录发送的消息 ^_^
            String msgId = UUID.randomUUID().toString();
            MailLog mailLog = new MailLog();
            mailLog.setMsgId(msgId);
            mailLog.setEmployeeId(employee.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT));
            mailLogMapper.insert(mailLog);

            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,emp,new CorrelationData(msgId));
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    /**
     * 获取员工信息
     * @param employeeId
     * @return
     */
    @Override
    public List<Employee> getEmployee(Integer employeeId) {
        return employeeMapper.getEmployee(employeeId);
    }

    /**
     * 获取所有员工账套
     * @param currentPage
     * @param size
     * @param name
     * @return
     */
    @Override
    public RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size, String name) {
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeeIPage = employeeMapper.getEmployeeWithSalary(page, name);
        return new RespPageBean(employeeIPage.getTotal(), employeeIPage.getRecords());
    }

    /**
     * 用于每月第一天发送定时任务
     * @return
     */
    @Override
    public List<Employee> getEmployeeWithSalary2() {
        return employeeMapper.getEmployeeWithSalary2();
    }

    /**
     * 获取所有员工当月工资信息
     * @param currentPage
     * @param size
     * @param depId
     * @return
     */
    @Override
    public RespPageBean getAllEmployeeWithSalaryTable(Integer currentPage, Integer size, Integer depId) {
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeeIPage = employeeMapper.getAllEmployeeWithSalaryTable(page, depId, LocalDate.now().getYear(), LocalDate.now().getMonthValue());
        return new RespPageBean(employeeIPage.getTotal(), employeeIPage.getRecords());
    }

    /**
     * 获取所有员工当月工资信息(不分页)
     * @param year
     * @param month
     * @return
     */
    @Override
    public List<Employee> getAllEmployeeWithSalaryTable2(int year, int month) {
        return employeeMapper.getAllEmployeeWithSalaryTable2(year, month);
    }

    /**
     * 获取所有工资表信息
     * @param currentPage
     * @param size
     * @param depId
     * @param localDate
     * @return
     */
    @Override
    public RespPageBean getAllSalaryTables(Integer currentPage, Integer size, Integer depId, LocalDate localDate) {
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeeIPage = employeeMapper.getAllEmployeeWithSalaryTable(page, depId, localDate.getYear(), localDate.getMonthValue());
        return new RespPageBean(employeeIPage.getTotal(), employeeIPage.getRecords());
    }

    /**
     * 职业人数统计
     * @return
     */
    @Override
    public List<RespChartBean> getPositionNumber() {
        return employeeMapper.getPositionNumber();
    }

    /**
     * 部门人员统计
     * @return
     */
    @Override
    public List<RespChartBean> getDepartmentNumber() {
        return employeeMapper.getDepartmentNumber();
    }

    /**
     * 人员构成分析-按年龄段统计
     * @param depId
     * @return
     */
    @Override
    public List<RespChartBean> getCompositionByAge(Integer depId) {
        return employeeMapper.getCompositionByAge(depId);
    }

    /**
     * 人员构成分析-按工龄统计
     * @param depId
     * @return
     */
    @Override
    public List<RespChartBean> getCompositionByWorkAge(Integer depId) {
        return employeeMapper.getCompositionByWorkAge(depId);
    }

    /**
     * 人员构成分析-按性别统计
     * @param depId
     * @return
     */
    @Override
    public List<RespChartBean> getCompositionByGender(Integer depId) {
        return employeeMapper.getCompositionByGender(depId);
    }

    /**
     * 人员构成分析-按最高学历统计
     * @param depId
     * @return
     */
    @Override
    public List<RespChartBean> getCompositionByDegree(Integer depId) {
        return employeeMapper.getCompositionByDegree(depId);
    }

    /**
     * 部门薪资统计
     * @param localDate
     * @return
     */
    @Override
    public List<RespChartBean> getSalaryDepartment(String localDate) {
        return employeeMapper.getSalaryDepartment(localDate);
    }

    /**
     * 职位薪资统计
     * @param localDate
     * @return
     */
    @Override
    public List<RespChartBean> getSalaryPosition(String localDate) {
        return employeeMapper.getSalaryPosition(localDate);
    }

    /**
     * 人事信息统计-员工结构统计-按聘用形式统计
     * @param depId
     * @return
     */
    @Override
    public List<RespChartBean> getCompositionByEngageForm(Integer depId) {
        return employeeMapper.getCompositionByEngageForm(depId);
    }

    /**
     * 员工结构统计-按在职状态统计
     * @param depId
     * @return
     */
    @Override
    public List<RespChartBean> getCompositionByWorkState(Integer depId) {
        return employeeMapper.getCompositionByWorkState(depId);
    }

    /**
     * 年龄统计-员工平均年龄
     * @param depId
     * @return
     */
    @Override
    public Integer getAverageAge(Integer depId) {
        return employeeMapper.getAverageAge(depId);
    }

    /**
     * 年龄统计-部门员工平均年龄统计
     * @return
     */
    @Override
    public List<RespChartBean> getDepartmentAverageAge() {
        return employeeMapper.getDepartmentAverageAge();
    }

    /**
     * 工龄统计-员工平均工龄统计
     * @param depId
     * @return
     */
    @Override
    public Integer getAverageWorkAge(Integer depId) {
        return employeeMapper.getAverageWorkAge(depId);
    }

    /**
     * 工龄统计-部门员工平均工龄统计
     * @return
     */
    @Override
    public List<RespChartBean> getDepartmentAverageWorkAge() {
        return employeeMapper.getDepartmentAverageWorkAge();
    }

    /**
     * 按婚姻状况统计
     * @param depId
     * @return
     */
    @Override
    public List<RespChartBean> getWedLock(Integer depId) {
        return employeeMapper.getWedLock(depId);
    }

    /**
     * 按民族进行统计
     * @param depId
     * @return
     */
    @Override
    public List<RespChartBean> getNation(Integer depId) {
        return employeeMapper.getNation(depId);
    }

    /**
     * 按政治面貌进行统计
     * @param depId
     * @return
     */
    @Override
    public List<RespChartBean> getPoliticStatus(Integer depId) {
        return employeeMapper.getPoliticStatus(depId);
    }
}
