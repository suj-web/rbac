package ${packageName}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.rbac.mapper.${mapperName};
import com.example.rbac.pojo.${pojoName};
import com.example.rbac.service.${serviceName};
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${serviceImplName}
 * @author suj
 * @since ${.now?date}
 */
@Service
public class ${serviceImplName} extends ServiceImpl<${mapperName}, ${pojoName}> implements ${serviceName} {

    @Autowired
    private ${mapperName} ${mapperName?uncap_first};

    /**
     * 分页查询
     * @param currentPage
     * @param size
     * @return
     */
    @Override
    public RespPageBean getAll${pojoName}(Integer currentPage, Integer size) {
        Page<${pojoName}> page = new Page<>(currentPage, size);
        IPage<${pojoName}> ${pojoName?uncap_first}IPage = ${mapperName?uncap_first}.selectPage(page,null);
        RespPageBean respPageBean = new RespPageBean(${pojoName?uncap_first}IPage.getTotal(), ${pojoName?uncap_first}IPage.getRecords())
        return respPageBean;
    }
}
