package com.example.rbac.exception;

import com.example.rbac.pojo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public RespBean mySqlException(SQLException e){
        if(e instanceof SQLIntegrityConstraintViolationException){
            log.error("该数据有关联数据,操作失败!");
            return RespBean.error("该数据有关联数据,操作失败!");
        }
        log.error("数据库异常,操作失败!");
        return RespBean.error("数据库异常,操作失败!");
    }
}
