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
@TableName("t_backup")
@ApiModel(value="Backup对象", description="")
public class Backup implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文件名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "文件存放路径")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "文件大小")
    @TableField("size")
    private Double size;

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