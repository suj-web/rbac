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
@TableName("t_salary_table")
@ApiModel(value="SalaryTable对象", description="")
public class SalaryTable implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工id")
    @TableField("employee_id")
    private Integer employeeId;

    @ApiModelProperty(value = "工资账套id")
    @TableField("salary_id")
    private Integer salaryId;

    @ApiModelProperty(value = "工资表时间")
    @TableField("date")
    private LocalDateTime date;

    @ApiModelProperty(value = "考勤扣款")
    @TableField("attendance_deduction")
    private Double attendanceDeduction;

    @ApiModelProperty(value = "请假扣款")
    @TableField("leave_deduction")
    private Double leaveDeduction;

    @ApiModelProperty(value = "奖金")
    @TableField("bonus")
    private Double bonus;

    @ApiModelProperty(value = "应发工资")
    @TableField("all_salary")
    private Double allSalary;

    @ApiModelProperty(value = "是否可修改 1可修改 0不可修改")
    @TableField("enabled")
    private Boolean enabled;

    @ApiModelProperty(value = "工资是否发放 0未发放 1已发放")
    @TableField("status")
    private Boolean status;

    @ApiModelProperty(value = "逻辑删除")
    @TableField("is_delete")
    private Boolean isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
}