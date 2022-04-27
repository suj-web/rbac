<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${mapperName}">

    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${packageName}.pojo.${pojoName}">
        <#if columns??>
            <#list columns as column>
        <<#if column.isPrimary??>id<#else>result</#if> column="${column.columnName}" property="${column.propertyName}" />
            </#list>
        </#if>
    </resultMap>

    <!-- 通用查询结果列 -->
    <sql id="Base_Column_List">
        <#list columns as column>${column.columnName}<#if column_has_next>, </#if></#list>
    </sql>
</mapper>
