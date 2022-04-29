package com.example.rbac.mapper;

import com.example.rbac.pojo.Chat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * 聊天对象Mapper
 * @author suj
 * @since 2022-4-27
 */
@Repository
public interface ChatMapper extends BaseMapper<Chat> {

}