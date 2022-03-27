package com.example.rbac.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author suj
 * @since 2022-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_login_log")
@ApiModel(value="LoginLog对象", description="")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "session_id")
    @Excel(name = "会话编号")
    private String sessionId;

    @ApiModelProperty(value = "登录名称")
    @Excel(name = "访问名称")
    private String name;

    @ApiModelProperty(value = "登录地址")
    @Excel(name = "登录地址",width = 24)
    private String ip;

    @ApiModelProperty(value = "登录地点")
    @Excel(name = "登录地点")
    private String address;

    @ApiModelProperty(value = "浏览器")
    @Excel(name = "浏览器")
    private String browser;

    @ApiModelProperty(value = "操作系统")
    @Excel(name = "操作系统")
    private String os;

    @ApiModelProperty(value = "登录状态 1成功 0失败")
    @Excel(name = "登录状态", replace = {"成功_1","失败_0"})
    private Boolean type;

    @ApiModelProperty(value = "操作信息")
    @TableField("oper_info")
    @Excel(name = "操作信息",width = 30)
    private String operInfo;

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


}
