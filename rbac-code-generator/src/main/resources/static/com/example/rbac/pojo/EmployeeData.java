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
@TableName("t_employee_data")
@ApiModel(value="EmployeeData对象", description="")
public class EmployeeData implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工id")
    @TableField("employee_id")
    private Integer employeeId;

    @ApiModelProperty(value = "纸质合同路径")
    @TableField("contract_path")
    private String contractPath;

    @ApiModelProperty(value = "身份证正反面path")
    @TableField("id_card_path")
    private String idCardPath;

    @ApiModelProperty(value = "体检报告path")
    @TableField("medical_report_path")
    private String medicalReportPath;

    @ApiModelProperty(value = "学历证书path")
    @TableField("degree_certificate_path")
    private String degreeCertificatePath;

    @ApiModelProperty(value = "offer存放路径")
    @TableField("offer_path")
    private String offerPath;

    @ApiModelProperty(value = "个人简历path")
    @TableField("resume_path")
    private String resumePath;

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