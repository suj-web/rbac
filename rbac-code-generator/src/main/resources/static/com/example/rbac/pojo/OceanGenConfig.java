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
@TableName("ocean_gen_config")
@ApiModel(value="OceanGenConfig对象", description="")
public class OceanGenConfig implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "")
    @TableField("id")
    private Long id;

    @ApiModelProperty(value = "前端api文件路径")
    @TableField("api_path")
    private String apiPath;

    @ApiModelProperty(value = "作者")
    @TableField("author")
    private String author;

    @ApiModelProperty(value = "是否覆盖")
    @TableField("cover")
    private Boolean cover;

    @ApiModelProperty(value = "模块名")
    @TableField("module_name")
    private String moduleName;

    @ApiModelProperty(value = "包路径")
    @TableField("pack")
    private String pack;

    @ApiModelProperty(value = "前端文件路径")
    @TableField("path")
    private String path;

    @ApiModelProperty(value = "表前缀")
    @TableField("prefix")
    private String prefix;
}