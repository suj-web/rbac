package com.example.rbac.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author suj
 * @since 2022-01-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_salary_adjust")
@ApiModel(value="SalaryAdjust对象", description="")
public class SalaryAdjust implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工ID")
    @TableField("employee_id")
    private Integer employeeId;

    @ApiModelProperty(value = "调薪日期")
    @TableField("as_date")
    private LocalDate asDate;

    @ApiModelProperty(value = "调前工资账套id")
    @TableField("before_salary_id")
    private Integer beforeSalaryId;

    @ApiModelProperty(value = "调后工资账套id")
    @TableField("after_salary_id")
    private Integer afterSalaryId;

    @ApiModelProperty(value = "调薪原因")
    private String reason;

    @ApiModelProperty(value = "调薪审核状态")
    private Integer status;

    @ApiModelProperty(value = "是否已调薪")
    @TableField("is_adjust")
    private Boolean isAdjust;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "逻辑删除 1删除 0未删除")
    @TableField("is_delete")
    @TableLogic
    private Boolean isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private LocalDateTime gmtModified;

    @ApiModelProperty(value = "员工信息")
    @TableField(exist = false)
    private Employee employee;

    @ApiModelProperty(value = "调前工资账套")
    @TableField(exist = false)
    private Salary beforeSalary;

    @ApiModelProperty(value = "调后工资账套")
    @TableField(exist = false)
    private Salary afterSalary;
}
