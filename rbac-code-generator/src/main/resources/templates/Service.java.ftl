package ${packageName}.service;

import ${packageName}.pojo.${pojoName};
import com.baomidou.mybatisplus.extension.service.IService;
import ${packageName}.pojo.RespPageBean;

/**
 * ${serviceName}
 * @author suj
 * @since ${.now?date}
 */
public interface ${serviceName} extends IService<${pojoName}> {

    /**
     * 分页查询
     * @param currentPage
     * @param size
     * @return
     */
    RespPageBean getAll${pojoName}(Integer currentPage, Integer size);
}