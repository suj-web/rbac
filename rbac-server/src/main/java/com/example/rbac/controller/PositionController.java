package com.example.rbac.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.Employee;
import com.example.rbac.pojo.Position;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.service.IEmployeeService;
import com.example.rbac.service.IPositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 *系统管理-基础信息设置-职位管理
 * @author suj
 * @since 2022-01-05
 */
@RestController
@RequestMapping("/system/basic/position")
public class PositionController {
    @Autowired
    private IPositionService positionService;

    @Autowired
    private IEmployeeService employeeService;

    @OperationLogAnnotation(operModul = "职位管理",operType = "查询",operDesc = "获取所有职位信息")
    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.list();
    }

    @OperationLogAnnotation(operModul = "职位管理",operType = "添加",operDesc = "添加职位信息")
    @ApiOperation(value = "添加职位信息")
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
        if(positionService.save(position)){
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @OperationLogAnnotation(operModul = "职位管理",operType = "更新",operDesc = "更新职位信息")
    @ApiOperation(value = "更新职位信息")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        if(positionService.updateById(position)){
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败!");
    }

    @OperationLogAnnotation(operModul = "职位管理",operType = "删除",operDesc = "删除职位信息")
    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id){
        List<Employee> employees = employeeService.list(new QueryWrapper<Employee>().eq("position_id", id));
        if(employees.size() > 0) {
            return RespBean.error("该数据有关联数据,操作失败!");
        }
        if(positionService.removeById(id)){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @OperationLogAnnotation(operModul = "职位管理",operType = "删除",operDesc = "批量删除职位信息")
    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public RespBean deletePositionByIds(Integer[] ids){
        List<Employee> employees = null;
        for(Integer id: ids) {
            employees = employeeService.list(new QueryWrapper<Employee>().eq("position_id", id));
            if(employees.size() > 0) {
                return RespBean.error("该数据有关联数据,操作失败!");
            }
        }
        if(positionService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
}
