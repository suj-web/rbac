package com.example.rbac.code.generator.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author suj
 * @create 2022/4/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableClass {
    private String tableName;
    private String pojoName;
    private String serviceName;
    private String serviceImplName;
    private String mapperName;
    private String controllerName;
    private String packageName;
    private String vueName;
    private List<ColumnClass> columns;
}
