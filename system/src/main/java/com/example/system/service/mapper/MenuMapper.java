package com.example.system.service.mapper;

import com.ocean.common.mapper.EntityMapper;
import com.example.system.domain.Menu;
import com.example.system.service.dto.MenuDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author suj
* @date 2022-03-28
*/
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapper extends EntityMapper<MenuDTO, Menu> {

}