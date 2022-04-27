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
@TableName("t_oplog")
@ApiModel(value="Oplog对象", description="")
public class Oplog implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "操作员")
    @TableField("operator")
    private String operator;

    @ApiModelProperty(value = "操作员ip地址")
    @TableField("ip")
    private String ip;

    @ApiModelProperty(value = "操作类型")
    @TableField("type")
    private String type;

    @ApiModelProperty(value = "操作内容")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "操作模块")
    @TableField("model")
    private String model;

    @ApiModelProperty(value = "操作结果")
    @TableField("result")
    private Integer result;

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