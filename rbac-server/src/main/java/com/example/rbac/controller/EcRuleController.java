package com.example.rbac.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.rbac.annotation.OperationLogAnnotation;
import com.example.rbac.pojo.*;
import com.example.rbac.service.IEcRuleService;
import com.example.rbac.service.IEmployeeEcService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统管理-基础信息设置-奖惩规则
 * @Author suj
 * @create 2022/3/18
 */
@RestController
@RequestMapping("/system/basic/ec/rule")
public class EcRuleController {

    @Autowired
    private IEcRuleService ecRuleService;

    @Autowired
    private IEmployeeEcService employeeEcService;

    @OperationLogAnnotation(operModul = "系统管理-基础信息设置-奖惩规则",operType = "查询",operDesc = "查询所有奖惩规则")
    @ApiOperation(value = "查询所有奖惩规则")
    @GetMapping("/list")
    public RespPageBean getEcRuleList(@RequestParam(defaultValue = "1") Integer currentPage,
                                      @RequestParam(defaultValue = "10") Integer size) {
        return ecRuleService.getEcRuleList(currentPage, size);
    }

    @OperationLogAnnotation(operModul = "系统管理-基础信息设置-奖惩规则",operType = "添加",operDesc = "添加所有奖惩规则")
    @ApiOperation(value = "添加奖惩规则")
    @PostMapping("/")
    public RespBean addEcRule(@RequestBody EcRule ecRule) {
        if(ecRuleService.save(ecRule)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @OperationLogAnnotation(operModul = "系统管理-基础信息设置-奖惩规则",operType = "修改",operDesc = "修改所有奖惩规则")
    @ApiOperation(value = "修改奖惩规则")
    @PutMapping("/")
    public RespBean updateEcRule(@RequestBody EcRule ecRule) {
        if(ecRuleService.updateById(ecRule)) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @OperationLogAnnotation(operModul = "系统管理-基础信息设置-奖惩规则",operType = "删除",operDesc = "删除所有奖惩规则")
    @ApiOperation(value = "删除奖惩规则")
    @DeleteMapping("/{id}")
    public RespBean deleteEcRuleById(@PathVariable Integer id) {
        List<EmployeeEc> list = employeeEcService.list(new QueryWrapper<EmployeeEc>().eq("ec_id", id));
        if(null != list && list.size() > 0) {
            return RespBean.error("该数据有关联数据,操作失败!");
        } else {
            if (ecRuleService.removeById(id)) {
                return RespBean.success("删除成功");
            }
        }
        return RespBean.error("删除失败");
    }

    @OperationLogAnnotation(operModul = "系统管理-基础信息设置-奖惩规则",operType = "删除",operDesc = "删除所有奖惩规则")
    @ApiOperation(value = "批量删除奖惩规则")
    @DeleteMapping("")
    public RespBean deleteEcRuleByIds(Integer[] ids) {
        for(Integer id: ids) {
            List<EmployeeEc> list = employeeEcService.list(new QueryWrapper<EmployeeEc>().eq("ec_id", id));
            if(null != list && list.size() > 0) {
                return RespBean.error("该数据有关联数据,操作失败!");
            }
        }
        if(ecRuleService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}
