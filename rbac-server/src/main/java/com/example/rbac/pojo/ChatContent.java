package com.example.rbac.pojo;

import com.baomidou.mybatisplus.annotation.*;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_chat_content")
@ApiModel(value="ChatContent对象", description="")
public class ChatContent implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "聊天对象id")
    @TableField("chat_obj_id")
    private Integer chatObjId;

    @ApiModelProperty(value = "聊天内容")
    @TableField("content")
    private String content;

    @ApiModelProperty(value = "时间")
    @TableField("date")
    private LocalDateTime date;

    @ApiModelProperty(value = "是否是自己发送的消息 1是 0不是")
    @TableField("self")
    private Boolean self;

    @ApiModelProperty(value = "是否已读 0未读 1已读")
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

    @ApiModelProperty(value = "聊天对象")
    @TableField(exist = false)
    private Chat chat;
}