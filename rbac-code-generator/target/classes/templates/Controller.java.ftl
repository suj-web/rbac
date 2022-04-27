package ${packageName}.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ${packageName}.service.${serviceName};
import ${packageName}.pojo.${pojoName};
import ${packageName}.pojo.RespPageBean;
import java.util.Arrays;

/**
 * ${controllerName}
 * @author suj
 * @since ${.now?date}
 */
@RestController
@RequestMapping("/${controllerName?uncap_first}")
public class ${controllerName} {

    @Autowired
    private ${serviceName} ${serviceName?substring(1)?uncap_first};

    @ApiOperation(value = "分页查询")
    @GetMapping("/list")
    public RespPageBean getAll${pojoName}(@RequestParam(defaultValue = "1") Integer currentPage,
                            @RequestParam(defaultValue = "10") Integer size) {
        return ${serviceName?substring(1)?uncap_first}.getAll${pojoName}(currentPage, size);
    }

    @ApiOperation(value = "添加")
    @PostMapping("/")
    public RespBean add${pojoName}(@RequestBody ${pojoName} ${pojoName?uncap_first}) {
        if(${serviceName?substring(1)?uncap_first}.save(${pojoName?uncap_first})) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "修改")
    @PutMapping("/")
    public RespBean update${pojoName}(@RequestBody ${pojoName} ${pojoName?uncap_first}) {
        if(${serviceName?substring(1)?uncap_first}.updateById(${pojoName?uncap_first})) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public RespBean delete${pojoName}ById(@PathVariable Integer id) {
        if(${serviceName?substring(1)?uncap_first}.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("/")
    public RespBean delete${pojoName}ByIds(Integer[] ids) {
        if(${serviceName?substring(1)?uncap_first}.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}