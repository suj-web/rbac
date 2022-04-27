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
@TableName("t_login_log")
@ApiModel(value="LoginLog对象", description="")
public class LoginLog implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "会话id")
    @TableField("session_id")
    private String sessionId;

    @ApiModelProperty(value = "登录名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "登录地址")
    @TableField("ip")
    private String ip;

    @ApiModelProperty(value = "登录地点")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "浏览器")
    @TableField("browser")
    private String browser;

    @ApiModelProperty(value = "操作系统")
    @TableField("os")
    private String os;

    @ApiModelProperty(value = "登录状态 1成功 0失败")
    @TableField("type")
    private Boolean type;

    @ApiModelProperty(value = "操作信息")
    @TableField("oper_info")
    private String operInfo;

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