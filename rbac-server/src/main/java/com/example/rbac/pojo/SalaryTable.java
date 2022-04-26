package com.example.rbac.pojo;

import java.math.BigDecimal;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.poi.hpsf.Decimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author suj
 * @since 2022-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_salary_table")
@ApiModel(value="SalaryTable对象", description="")
public class SalaryTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工id")
    @TableField("employee_id")
    private Integer employeeId;

    @ApiModelProperty(value = "工资账套id")
    @TableField("salary_id")
    private Integer salaryId;

    @ApiModelProperty(value = "工资表时间")
    @JsonFormat(pattern = "yyyy-MM",timezone = "Asia/Shanghai")
    private LocalDateTime date;

    @ApiModelProperty(value = "出勤扣款")
    @TableField("attendance_deduction")
    private double attendanceDeduction;

    @ApiModelProperty(value = "请假扣款")
    @TableField("leave_deduction")
    private double leaveDeduction;

    @ApiModelProperty(value = "奖金")
    private double bonus;

    @ApiModelProperty(value = "应发工资")
    private double allSalary;

    @ApiModelProperty(value = "是否可修改 0可修改 1不可修改")
    private Boolean enabled;

    @ApiModelProperty(value = "工资是否已发放")
    private Boolean status;

    @ApiModelProperty(value = "逻辑删除")
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

    @ApiModelProperty(value = "工资账套")
    @TableField(exist = false)
    private Salary salary;

    @ApiModelProperty(value = "员工信息")
    @TableField(exist = false)
    private Employee employee;
}
