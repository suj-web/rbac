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

    private String name;//类型
    private Integer value;//数量
    private Double average;//平均值(平均工资, 平均工龄,平均年龄)
}
