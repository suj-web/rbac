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

/**
 * @author suj
 * @since 2022-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_oplog")
@ApiModel(value="Oplog对象", description="")
public class Oplog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "操作员")
    private String operator;

    @ApiModelProperty(value = "操作员ip地址")
    private String ip;

    @ApiModelProperty(value = "操作类型")
    private String type;

    @ApiModelProperty(value = "操作内容")
    private String description;

    @ApiModelProperty(value = "操作模块")
    private String model;

    @ApiModelProperty(value = "操作结果")
    private String result;

    @ApiModelProperty(value = "逻辑删除 1删除 0未删除")
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
