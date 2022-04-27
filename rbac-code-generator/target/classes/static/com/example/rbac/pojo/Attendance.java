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
@TableName("t_attendance")
@ApiModel(value="Attendance对象", description="")
public class Attendance implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "员工id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工id")
    @TableField("employee_id")
    private Integer employeeId;

    @ApiModelProperty(value = "上班打卡时间")
    @TableField("punch_in_time")
    private LocalDateTime punchInTime;

    @ApiModelProperty(value = "下班打卡时间")
    @TableField("punch_out_time")
    private LocalDateTime punchOutTime;

    @ApiModelProperty(value = "上班考勤时间")
    @TableField("work_attendance_time")
    private LocalDateTime workAttendanceTime;

    @ApiModelProperty(value = "下班考勤时间")
    @TableField("off_duty_attendance_time")
    private LocalDateTime offDutyAttendanceTime;

    @ApiModelProperty(value = "事假")
    @TableField("personal_leave")
    private Integer personalLeave;

    @ApiModelProperty(value = "病假")
    @TableField("sick_leave")
    private Integer sickLeave;

    @ApiModelProperty(value = "缺勤 0 未缺勤 1缺勤")
    @TableField("absenteeism")
    private Boolean absenteeism;

    @ApiModelProperty(value = "逻辑删除 0未删除")
    @TableField("is_delete")
    private Boolean isDelete;

    @ApiModelProperty(value = "创建时间")
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;
}