package com.example.rbac.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 导入,导出打卡记录
 * @Author suj
 * @create 2022/4/3
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AttendanceExcel对象", description = "")
public class AttendanceExcel implements Serializable {

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

    @ApiModelProperty(value = "上班打卡时间")
    @Excel(name = "上班打卡时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime attendanceTime;

    @ApiModelProperty(value = "下班打卡时间")
    @Excel(name = "下班打卡时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime closingTime;

    @ApiModelProperty(value = "事假")
    @Excel(name = "事假")
    private Integer personalLeave;

    @ApiModelProperty(value = "病假(天)")
    @Excel(name = "病假(天)")
    private Integer sickLeave;

    @ApiModelProperty(value = "出勤")
    @Excel(name = "出勤",replace = {"出勤_0","缺勤_1"})
    private Boolean absenteeism;

    @ApiModelProperty(value = "考勤时间")
    @Excel(name = "考勤时间", width = 20, format = "yyyy-MM-dd")
    private LocalDateTime date;
}
