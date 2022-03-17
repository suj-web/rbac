package com.example.rbac.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回数据表数据
 * @Author suj
 * @create 2022/3/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespChartBean {

    private String name;
    private Integer count;

}
