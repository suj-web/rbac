package com.example.rbac.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回员工异动实体类
 * @Author suj
 * @create 2022/3/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespEmployeeRecordBean {

    @ApiModelProperty(value = "部门名称")
    @Excel(name = "部门名称",width = 20)
    private String name;

    @ApiModelProperty(value = "期初人数")
    @Excel(name = "期初人数", width = 20)
    private Integer beginCount;

    @ApiModelProperty(value = "期末人数")
    @Excel(name = "期末人数", width = 20)
    private Integer endCount;

    @ApiModelProperty(value = "入职人数")
    @Excel(name = "入职人数", width = 20)
    private Integer entryCount;

    @ApiModelProperty(value = "入职率")
    @Excel(name = "入职率", width = 20, suffix = "%")
    private Double inductionRate;

    @ApiModelProperty(value = "转正人数")
    @Excel(name = "转正人数", width = 20)
    private Integer conversionCount;

    @ApiModelProperty(value = "离职人数")
    @Excel(name = "离职人数", width = 20)
    private Integer dimissionCount;

    @ApiModelProperty(value = "离职率")
    @Excel(name = "离职率", width = 20, suffix = "%")
    private Double dimissionRate;

    @ApiModelProperty(value = "调入人数")
    @Excel(name = "调入人数", width = 20)
    private Integer foldCount;

    @ApiModelProperty(value = "调离人数")
    @Excel(name = "调离人数", width = 20)
    private Integer transferCount;
}
