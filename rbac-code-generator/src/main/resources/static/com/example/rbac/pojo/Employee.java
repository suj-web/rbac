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
@TableName("t_employee")
@ApiModel(value="Employee对象", description="")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "工号")
    @TableField("work_id")
    private String workId;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "手机号码")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "住宅号码")
    @TableField("telephone")
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "联系地址")
    @TableField("address")
    private String address;

    @ApiModelProperty(value = "身份证号")
    @TableField("id_card")
    private String idCard;

    @ApiModelProperty(value = "所属部门")
    @TableField("department_id")
    private Integer departmentId;

    @ApiModelProperty(value = "职位id")
    @TableField("position_id")
    private Integer positionId;

    @ApiModelProperty(value = "民族")
    @TableField("nation_id")
    private Integer nationId;

    @ApiModelProperty(value = "籍贯")
    @TableField("native_place")
    private String nativePlace;

    @ApiModelProperty(value = "政治面貌id")
    @TableField("politic_id")
    private Integer politicId;

    @ApiModelProperty(value = "职称id")
    @TableField("job_level_id")
    private Integer jobLevelId;

    @ApiModelProperty(value = "聘用形式")
    @TableField("engage_form")
    private String engageForm;

    @ApiModelProperty(value = "所属专业")
    @TableField("specialty")
    private String specialty;

    @ApiModelProperty(value = "毕业院校")
    @TableField("school")
    private String school;

    @ApiModelProperty(value = "头像")
    @TableField("user_face")
    private String userFace;

    @ApiModelProperty(value = "简介")
    @TableField("remark")
    private String remark;

    @ApiModelProperty(value = "合同期限")
    @TableField("contract_term")
    private Double contractTerm;

    @ApiModelProperty(value = "工龄")
    @TableField("work_age")
    private Integer workAge;

    @ApiModelProperty(value = "工资账套id")
    @TableField("salary_id")
    private Integer salaryId;

    @ApiModelProperty(value = "开户姓名")
    @TableField("account_name")
    private String accountName;

    @ApiModelProperty(value = "银行账户")
    @TableField("bank_account")
    private String bankAccount;

    @ApiModelProperty(value = "开户银行")
    @TableField("bank")
    private String bank;

    @ApiModelProperty(value = "是否启用")
    @TableField("enabled")
    private Boolean enabled;

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