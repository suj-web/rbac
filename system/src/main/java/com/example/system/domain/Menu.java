package com.example.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.io.Serializable;

/**
* @author suj
* @date 2022-03-28
*/
@Entity
@Data
@Table(name="menu")
public class Menu implements Serializable {

    // 主键id
    @Id
    @Column(name = "id")
    private Long id;

    public void copy(Menu source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}