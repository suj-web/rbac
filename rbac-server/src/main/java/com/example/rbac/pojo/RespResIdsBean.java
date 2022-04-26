package com.example.rbac.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author suj
 * @create 2022/4/21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespResIdsBean {
    private Integer roleId;
    private List<Integer> resIds;
}
