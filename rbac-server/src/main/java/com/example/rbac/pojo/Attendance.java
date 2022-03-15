package com.example.rbac.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
 * @since 2022-03-08
 */
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

    @ApiModelProperty(value = "上班时间")
    @TableField("attendance_time")
    private LocalDateTime attendanceTime;

    @ApiModelProperty(value = "下班时间")
    @TableField("closing_time")
    private LocalDateTime closingTime;

    @ApiModelProperty(value = "事假")
    @TableField("personal_leave")
    private String personalLeave;

    @ApiModelProperty(value = "病假")
    @TableField("sick_leave")
    private String sickLeave;

    @ApiModelProperty(value = "旷工")
    private String absenteeism;

    @ApiModelProperty(value = "创建时间")
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField("gmt_modified")
    private LocalDateTime gmtModified;


}
