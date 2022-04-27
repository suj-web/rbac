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
@TableName("t_mail_log")
@ApiModel(value="MailLog对象", description="")
public class MailLog implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "消息id")
    @TableField("msg_id")
    private String msgId;

    @ApiModelProperty(value = "接收员工id")
    @TableField("employee_id")
    private Integer employeeId;

    @ApiModelProperty(value = "状态（0:消息投递中 1:投递成功 2:投递失败）")
    @TableField("status")
    private Integer status;

    @ApiModelProperty(value = "路由键")
    @TableField("route_key")
    private String routeKey;

    @ApiModelProperty(value = "交换机")
    @TableField("exchange")
    private String exchange;

    @ApiModelProperty(value = "重试次数")
    @TableField("count")
    private Integer count;

    @ApiModelProperty(value = "重试时间")
    @TableField("try_time")
    private LocalDateTime tryTime;

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