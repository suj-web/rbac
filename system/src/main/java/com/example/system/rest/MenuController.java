package com.example.system.rest;

import com.example.system.domain.Menu;
import com.example.system.service.MenuService;
import com.example.system.service.dto.MenuQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

/**
* @author suj
* @date 2022-03-28
*/
@Api(tags = "Menu管理")
@RestController
@RequestMapping("api")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @ApiOperation(value = "查询Menu")
    @GetMapping(value = "/menu")
    public ResponseEntity getMenus(MenuQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity(menuService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @ApiOperation(value = "新增Menu")
    @PostMapping(value = "/menu")
    public ResponseEntity create(@Validated @RequestBody Menu resources){
        return new ResponseEntity(menuService.create(resources),HttpStatus.CREATED);
    }

    @ApiOperation(value = "修改Menu")
    @PutMapping(value = "/menu")
    public ResponseEntity update(@Validated @RequestBody Menu resources){
        menuService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除Menu")
    @DeleteMapping(value = "/menu/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        menuService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}