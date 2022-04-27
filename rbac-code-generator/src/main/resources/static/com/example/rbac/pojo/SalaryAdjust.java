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

    @ApiModelProperty(value = "调前工资账套")
    @TableField("before_salary_id")
    private Integer beforeSalaryId;

    @ApiModelProperty(value = "调后工资账套")
    @TableField("after_salary_id")
    private Integer afterSalaryId;

    @ApiModelProperty(value = "调薪原因")
    @TableField("reason")
    private String reason;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "调薪审核状态 0待审核 1通过, 2未通过")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty(value = "是否已调薪 0待调薪 1已调薪")
    @TableField("is_adjust")
    private Boolean isAdjust;

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