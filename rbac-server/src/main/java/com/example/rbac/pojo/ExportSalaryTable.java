package com.example.rbac.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 导出当月账单表
 * @Author suj
 * @create 2022/3/8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ExportSalaryTable对象", description = "")
public class ExportSalaryTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工号")
    @TableField("work_id")
    @Excel(name = "工号",width = 20)
    private String workId;

    @ApiModelProperty(value = "姓名")
    @Excel(name = "姓名")
    private String name;

    @ApiModelProperty(value = "部门名称")
    @Excel(name = "部门", width = 20)
    private String depName;

    @ApiModelProperty(value = "年份")
    @Excel(name = "年份")
    private Integer year;

    @ApiModelProperty(value = "月份")
    @Excel(name = "月份")
    private Integer month;

    @ApiModelProperty(value = "奖金")
    @Excel(name = "奖金")
    private Double bonus;

    @ApiModelProperty(value = "应发工资")
    @Excel(name = "应发工资")
    private Double allSalary;
}
