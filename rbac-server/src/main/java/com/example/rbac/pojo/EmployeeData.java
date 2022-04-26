package com.example.rbac.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author suj
 * @since 2022-04-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_employee_data")
@ApiModel(value="EmployeeData对象", description="")
public class EmployeeData implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "员工id")
    @TableField("employee_id")
    private Integer employeeId;

    @ApiModelProperty(value = "纸质合同路径")
    @TableField("contract_path")
    private String contractPath;

    @ApiModelProperty(value = "身份证正反面path")
    @TableField(value = "id_card_path", updateStrategy = FieldStrategy.IGNORED)
    private String idCardPath;

    @ApiModelProperty(value = "体检报告path")
    @TableField(value = "medical_report_path", updateStrategy = FieldStrategy.IGNORED)
    private String medicalReportPath;

    @ApiModelProperty(value = "学历证书path")
    @TableField(value = "degree_certificate_path", updateStrategy = FieldStrategy.IGNORED)
    private String degreeCertificatePath;

    @ApiModelProperty(value = "offer存放路径")
    @TableField(value = "offer_path", updateStrategy = FieldStrategy.IGNORED)
    private String offerPath;

    @ApiModelProperty(value = "个人简历path")
    @TableField(value = "resume_path", updateStrategy = FieldStrategy.IGNORED)
    private String resumePath;

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

    @ApiModelProperty(value = "员工信息")
    @TableField(exist = false)
    private Employee employee;
}
