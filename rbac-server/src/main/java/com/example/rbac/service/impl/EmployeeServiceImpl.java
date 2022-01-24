package com.example.rbac.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.mapper.EmployeeRoleMapper;
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
    private EmployeeRoleMapper employeeRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MailLogMapper mailLogMapper;

    @Autowired
    private IsAdmin isAdmin;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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
     * 根据员工id获取角色信息
     * @param employeeId
     * @return
     */
    @Override
    public List<Role> getRolesByEmployeeId(Integer employeeId) {
        return roleMapper.getRolesByEmployeeId(employeeId);
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

    @Override
    public RespBean updateEmployeePassword(String oldPass, String pass, Integer employeeId) {
        Employee employee = employeeMapper.selectById(employeeId);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        //判断旧密码是否正确
        if(encoder.matches(oldPass,employee.getPassword())){
            employee.setPassword(encoder.encode(pass));
            int result = employeeMapper.updateById(employee);
            if(1==result){
                return RespBean.success("更新成功!");
            }
        }

        return RespBean.error("更新失败!");
    }

    /**
     * 更新员工头像
     * @param url
     * @param id
     * @param authentication
     * @return
     */
    @Override
    public RespBean updateEmployeeUserFace(String url, Integer id, Authentication authentication) {
        Employee employee = employeeMapper.selectById(id);
        employee.setUserFace(url);
        if(1 == employeeMapper.updateById(employee)) {
            Employee principal = (Employee) authentication.getPrincipal();
            principal.setUserFace(url);
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(principal, null, authentication.getAuthorities())
            );
            return RespBean.success("更新成功",url);
        }
        return RespBean.error("更新失败");
    }

    /**
     * 获取所有员工账套
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size) {
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeeIPage = employeeMapper.getEmployeeWithSalary(page);
        return new RespPageBean(employeeIPage.getTotal(), employeeIPage.getRecords());
    }

    /**
     * 根据关键字获取员工
     * @param keywords
     * @return
     */
    @Override
    public List<Employee> getAllEmployees(String keywords) {
        if(!isAdmin.getIsAdmin()) {
            Integer employeeId = ((Employee) UserUtils.getCurrentUser()).getId();
            return employeeMapper.getAllEmployees(employeeId, keywords);
        }
        return employeeMapper.getAllEmployees(null, keywords);
    }

    /**
     * 获取所有员工信息(带用户角色)
     * @param currentPage
     * @param size
     * @param name
     * @return
     */
    @Override
    public RespPageBean getEmployeeWithRoleByPage(Integer currentPage, Integer size, String name) {
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeeByPage = employeeMapper.getEmployeeWithRoleByPage(page, name);
        return new RespPageBean(employeeByPage.getTotal(), employeeByPage.getRecords());
    }

    /**
     * 更新员工角色
     * @param employeeId
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateEmployeeRole(Integer employeeId, Integer[] ids) {
        employeeRoleMapper.delete(new QueryWrapper<EmployeeRole>().eq("employee_id",employeeId));

        if(null == ids || 0 == ids.length){
            redisTemplate.delete(redisTemplate.keys("menu_*"));
            return RespBean.success("更新成功");
        }
        int result = employeeRoleMapper.insertEmployeeRole(employeeId, ids);
        if(ids.length == result) {
            redisTemplate.delete(redisTemplate.keys("menu_*"));
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }
}
