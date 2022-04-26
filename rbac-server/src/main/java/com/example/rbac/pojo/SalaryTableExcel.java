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
import java.time.LocalDateTime;

/**
 * 导出当月账单表
 * @Author suj
 * @create 2022/3/8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "ExportSalaryTable对象", description = "")
public class SalaryTableExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工号")
    @Excel(name = "工号", width = 20)
    private String workId;

    @ApiModelProperty(value = "姓名")
    @Excel(name = "姓名", width = 20)
    private String name;

    @ApiModelProperty(value = "所属部门")
    @Excel(name = "所属部门", width = 20)
    private String depName;

    @ApiModelProperty(value = "职位")
    @Excel(name = "职位", width = 20)
    private String position;

    @ApiModelProperty(value = "基本工资")
    @Excel(name = "基本工资", width = 20)
    private Double basicSalary;

    @ApiModelProperty(value = "午餐补助")
    @Excel(name = "午餐补助", width = 20)
    private Double lunchSalary;

    @ApiModelProperty(value = "交通补助")
    @Excel(name = "交通补助", width = 20)
    private Double trafficSalary;

    @ApiModelProperty(value = "养老金基数")
    @Excel(name = "养老金基数", width = 20)
    private Double pensionBase;

    @ApiModelProperty(value = "养老金比率")
    @Excel(name = "养老金比率", width = 20)
    private Double pensionPer;

    @ApiModelProperty(value = "医疗基数")
    @Excel(name = "医疗基数", width = 20)
    private Double medicalBase;

    @ApiModelProperty(value = "医疗保险比率")
    @Excel(name = "医疗保险比率", width = 20)
    private Double medicalPer;

    @ApiModelProperty(value = "公积金基数")
    @Excel(name = "公积金基数", width = 20)
    private Double accumulationFundBase;

    @ApiModelProperty(value = "公积金比率")
    @Excel(name = "公积金比率", width = 20)
    private Double accumulationFundPer;

    @ApiModelProperty(value = "出勤扣款")
    @Excel(name = "出勤扣款", width = 20)
    private double attendanceDeduction;

    @ApiModelProperty(value = "请假扣款")
    @Excel(name = "请假扣款", width = 20)
    private double leaveDeduction;

    @ApiModelProperty(value = "奖金")
    @Excel(name = "奖金", width = 20)
    private Double bonus;

    @ApiModelProperty(value = "应发工资")
    @Excel(name = "应发工资", width = 20)
    private Double allSalary;

    @ApiModelProperty(value = "账单月份")
    @Excel(name = "账单月份",format = "yyyy-MM", width = 20)
    private LocalDateTime date;
}
