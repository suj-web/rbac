package com.example.system.service.dto;

import lombok.Data;
import com.ocean.common.annotation.Query;

/**
* @author suj
* @date 2022-03-28
*/
@Data
public class MenuQueryCriteria{

    // 精确
    @Query
    private Long id;
}