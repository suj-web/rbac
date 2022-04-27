package com.example.rbac.code.generator.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author suj
 * @create 2022/4/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnClass {
    private String propertyName;
    private String columnName;
    private String type;
    private String remark;
    private Boolean isPrimary;
}
