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
    @Excel(name = "工号",width = 20)
    private String workId;

    @ApiModelProperty(value = "姓名")
    @Excel(name = "姓名")
    private String name;

    @ApiModelProperty(value = "所属部门")
    @Excel(name = "所属部门", width = 20)
    private String depName;

    @ApiModelProperty(value = "职位")
    @Excel(name = "职位", width = 20)
    private String position;

    @ApiModelProperty(value = "账单月份")
    @Excel(name = "账单月份",format = "yyyy-MM")
    private LocalDateTime date;

    @ApiModelProperty(value = "奖金")
    @Excel(name = "奖金")
    private Double bonus;

    @ApiModelProperty(value = "应发工资")
    @Excel(name = "应发工资")
    private Double allSalary;
}
