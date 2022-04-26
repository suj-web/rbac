package com.example.rbac.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author suj
 * @create 2022/4/4
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RespAttendanceTable对象", description="")
public class RespAttendanceTable implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工号")
    private String workId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "所属部门")
    private String depName;

    @ApiModelProperty(value = "职位")
    private String posName;

    @ApiModelProperty(value = "上班考勤时间")
    private LocalDateTime workAttendanceTime;

    @ApiModelProperty(value = "下班考勤时间")
    private LocalDateTime offDutyAttendanceTime;

    @ApiModelProperty(value = "上班打卡时间")
    private LocalDateTime punchInTime;

    @ApiModelProperty(value = "下班打卡时间")
    private LocalDateTime punchOutTime;

    @ApiModelProperty(value = "事假(天)")
    private Integer personalLeave;

    @ApiModelProperty(value = "病假(天)")
    private Integer sickLeave;

    @ApiModelProperty(value = "应出勤小时数")
    private Double requiredAttendanceHours;

    @ApiModelProperty(value = "实出勤小时数")
    private Double actualAttendanceHours;

    @ApiModelProperty(value = "迟到次数")
    private Integer lateTimes;

    @ApiModelProperty(value = "迟到分钟数")
    private Double lateMinutes;

    @ApiModelProperty(value = "早退次数")
    private Integer leaveEarlyTimes;

    @ApiModelProperty(value = "早退分钟数")
    private Double leaveEarlyMinutes;

    @ApiModelProperty(value = "缺勤小时数")
    private Double absenteeismHours;

    @ApiModelProperty(value = "缺勤次数")
    private Integer absenteeismTimes;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime gmtModified;
}

