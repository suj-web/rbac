package com.example.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.Joblevel;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.IJoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 *系统管理-基础信息设置-职称管理
 * @author suj
 * @since 2022-01-07
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JoblevelController {
    @Autowired
    private IJoblevelService joblevelService;

    @Autowired
    private IEmployeeService employeeService;

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/")
    public List<Joblevel> getAllJoblevels(){
        return joblevelService.list();
    }

    @ApiOperation(value = "添加职称")
    @PostMapping("/")
    public RespBean addJoblevel(@RequestBody Joblevel joblevel){
        if(joblevelService.save(joblevel)){
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @ApiOperation(value = "更新职称")
    @PutMapping("/")
    public RespBean updateJoblevel(@RequestBody Joblevel joblevel){
        if(joblevelService.updateById(joblevel)){
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @ApiOperation(value = "删除职称")
    @DeleteMapping("/{id}")
    public RespBean deleteJoblevel(@PathVariable Integer id){
        List<Employee> employees = employeeService.list(new QueryWrapper<Employee>().eq("job_level_id", id));
        if(employees.size() > 0) {
            return RespBean.error("该数据有关联数据,操作失败!");
        }
        if(joblevelService.removeById(id)){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @ApiOperation(value = "批量删除职称")
    @DeleteMapping("/")
    public RespBean deleteJoblevelByIds(Integer[] ids){
        List<Employee> employees = null;
        for(Integer id: ids) {
            employees = employeeService.list(new QueryWrapper<Employee>().eq("job_level_id", id));
            if(employees.size() > 0) {
                return RespBean.error("该数据有关联数据,操作失败!");
            }
        }
        if(joblevelService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
