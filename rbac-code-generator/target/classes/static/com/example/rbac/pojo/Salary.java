package com.example.rbac.pojo;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_salary")
@ApiModel(value="Salary对象", description="")
public class Salary implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "基本工资")
    @TableField("basic_salary")
    private Double basicSalary;

    @ApiModelProperty(value = "午餐补助")
    @TableField("lunch_salary")
    private Double lunchSalary;

    @ApiModelProperty(value = "交通补助")
    @TableField("traffic_salary")
    private Double trafficSalary;

    @ApiModelProperty(value = "养老金基数")
    @TableField("pension_base")
    private Double pensionBase;

    @ApiModelProperty(value = "养老金比率")
    @TableField("pension_per")
    private Double pensionPer;

    @ApiModelProperty(value = "医疗基数")
    @TableField("medical_base")
    private Double medicalBase;

    @ApiModelProperty(value = "医疗保险比率")
    @TableField("medical_per")
    private Double medicalPer;

    @ApiModelProperty(value = "公积金基数")
    @TableField("accumulation_fund_base")
    private Double accumulationFundBase;

    @ApiModelProperty(value = "公积金比率")
    @TableField("accumulation_fund_per")
    private Double accumulationFundPer;

    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "逻辑删除 1删除 0未删除")
    @TableField("is_delete")
    private Boolean isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
}