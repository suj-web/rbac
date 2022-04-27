package ${packageName}.pojo;

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
@TableName("${tableName}")
@ApiModel(value="${pojoName}对象", description="")
public class ${pojoName} implements Serializable {
    private static final long serialVersionUID = 1L;

<#if columns??>
    <#list columns as column>
        <#if column.type='VARCHAR'||column.type='TEXT'||column.type='CHAR'>

    @ApiModelProperty(value = "${column.remark}")
    @TableField("${column.columnName}")
    private String ${column.propertyName?uncap_first};
        </#if>
        <#if column.type='INT'>
            <#if column.isPrimary??>

    @ApiModelProperty(value = "${column.remark}")
    @TableId(value = "${column.columnName}", type = IdType.AUTO)
    private Integer ${column.propertyName?uncap_first};
            <#else>

    @ApiModelProperty(value = "${column.remark}")
    @TableField("${column.columnName}")
    private Integer ${column.propertyName?uncap_first};
            </#if>
        </#if>
        <#if column.type='DATETIME'>

    @ApiModelProperty(value = "${column.remark}")
    @TableField("${column.columnName}")
    private LocalDateTime ${column.propertyName?uncap_first};
        </#if>
        <#if column.type='BIGINT'>

    @ApiModelProperty(value = "${column.remark}")
    @TableField("${column.columnName}")
    private Long ${column.propertyName?uncap_first};
        </#if>
        <#if column.type='DOUBLE'>

    @ApiModelProperty(value = "${column.remark}")
    @TableField("${column.columnName}")
    private Double ${column.propertyName?uncap_first};
        </#if>
        <#if column.type='BIT'>

    @ApiModelProperty(value = "${column.remark}")
    @TableField("${column.columnName}")
    private Boolean ${column.propertyName?uncap_first};
        </#if>
        <#if column.type='TINYINT'>

    @ApiModelProperty(value = "${column.remark}")
    @TableField("${column.columnName}")
    private Boolean ${column.propertyName?uncap_first};
        </#if>
    </#list>
</#if>
}