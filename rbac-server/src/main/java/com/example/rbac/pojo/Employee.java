package com.example.rbac.pojo;

import cn.afterturn.easypoi.excel.annotation.*;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.example.rbac.config.CustomAuthorityDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_employee")
@ApiModel(value="Employee对象", description="")
public class Employee implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "工号")
    @TableField("work_id")
    @Excel(name = "工号")
    private String workId;

    @ApiModelProperty(value = "姓名")
    @Excel(name = "姓名")
    private String name;

    @ApiModelProperty(value = "用户名")
    @Excel(name = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "手机号码")
    @Excel(name = "手机号码",width = 20)
    private String phone;

    @ApiModelProperty(value = "住宅号码")
    @Excel(name = "住宅号码",width = 20)
    private String telephone;

    @ApiModelProperty(value = "邮箱")
    @Excel(name = "邮箱",width = 20)
    private String email;

    @ApiModelProperty(value = "联系地址")
    @Excel(name = "联系地址",width = 35)
    private String address;

    @ApiModelProperty(value = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "出生日期",width = 20)
    private LocalDate birthday;

    @ApiModelProperty(value = "婚姻状况")
    @TableField("wed_lock")
    @Excel(name = "婚姻状况")
    private String wedLock;

    @ApiModelProperty(value = "身份证号")
    @TableField("id_card")
    @Excel(name = "身份证号",width = 30)
    private String idCard;

    @ApiModelProperty(value = "所属部门id")
    @TableField("department_id")
    private Integer departmentId;

    @ApiModelProperty(value = "职位id")
    @TableField("position_id")
    private Integer positionId;

    @ApiModelProperty(value = "民族id")
    @TableField("nation_id")
    private Integer nationId;

    @ApiModelProperty(value = "籍贯")
    @TableField("native_place")
    @Excel(name = "籍贯")
    private String nativePlace;

    @ApiModelProperty(value = "政治面貌id")
    @TableField("politic_id")
    private Integer politicId;

    @ApiModelProperty(value = "职称id")
    @TableField("job_level_id")
    private Integer jobLevelId;

    @ApiModelProperty(value = "聘用形式")
    @TableField("engage_form")
    @Excel(name = "聘用形式")
    private String engageForm;

    @ApiModelProperty(value = "最高学历")
    @TableField("tiptop_degree")
    @Excel(name = "最高学历")
    private String tiptopDegree;

    @ApiModelProperty(value = "所属专业")
    @Excel(name = "所属专业",width = 26)
    private String specialty;

    @ApiModelProperty(value = "毕业院校")
    @Excel(name = "毕业院校",width = 30)
    private String school;

    @ApiModelProperty(value = "性别")
    @Excel(name = "性别")
    private String gender;

    @ApiModelProperty(value = "入职日期")
    @TableField("begin_date")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "入职日期",width = 20, format = "yyyy-MM-dd")
    private LocalDate beginDate;

    @ApiModelProperty(value = "离职日期")
    @TableField("not_work_date")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "离职日期",width = 20, format = "yyyy-MM-dd")
    private LocalDate notWorkDate;

    @ApiModelProperty(value = "头像")
    @TableField("user_face")
    private String userFace;

    @ApiModelProperty(value = "在职状态")
    @TableField("work_state")
    @Excel(name = "在职状态")
    private String workState;

    @ApiModelProperty(value = "简介")
    @Excel(name = "简介",width = 30)
    private String remark;

    @ApiModelProperty(value = "合同期限")
    @TableField("contract_term")
    @Excel(name = "合同期限")
    private Double contractTerm;

    @ApiModelProperty(value = "转正日期")
    @TableField("conversion_time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "转正日期",width = 20, format = "yyyy-MM-dd")
    private LocalDate conversionTime;

    @ApiModelProperty(value = "合同起始日期")
    @TableField("begin_contract")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "合同起始日期",width = 20, format = "yyyy-MM-dd")
    private LocalDate beginContract;

    @ApiModelProperty(value = "合同终止日期")
    @TableField("end_contract")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Excel(name = "合同终止日期",width = 20, format = "yyyy-MM-dd")
    private LocalDate endContract;

    @ApiModelProperty(value = "工龄")
    @TableField("work_age")
    @Excel(name = "工龄")
    private Integer workAge;

    @ApiModelProperty(value = "工资账套id")
    @TableField("salary_id")
    private Integer salaryId;

    @ApiModelProperty(value = "是否启用")
    @Getter(AccessLevel.NONE)
    private Boolean enabled;

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

    @ApiModelProperty(value = "部门")
    @TableField(exist = false)
    @ExcelEntity(name = "部门")
    private Department department;

    @ApiModelProperty(value = "职位")
    @TableField(exist = false)
    @ExcelEntity(name = "职位")
    private Position position;

    @ApiModelProperty(value = "民族")
    @TableField(exist = false)
    @ExcelEntity(name = "民族")
    private Nation nation;

    @ApiModelProperty(value = "政治面貌")
    @TableField(exist = false)
    @ExcelEntity(name = "政治面貌")
    private PoliticsStatus politicsStatus;

    @ApiModelProperty(value = "职称")
    @TableField(exist = false)
    @ExcelEntity(name = "职称")
    private Joblevel joblevel;

    @ApiModelProperty(value = "工资账套")
    @TableField(exist = false)
    private Salary salary;

    @ApiModelProperty(value = "工资表")
    @TableField(exist = false)
    @ExcelCollection(name = "工资表")
    private List<SalaryTable> salaryTables;
}
