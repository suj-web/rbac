package com.example.rbac.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.rbac.service.IChatContentService;
import com.example.rbac.pojo.ChatContent;
import com.example.rbac.pojo.RespPageBean;
import java.util.Arrays;

/**
 * ChatContentController
 * @author suj
 * @since 2022-4-27
 */
@RestController
@RequestMapping("/chatContentController")
public class ChatContentController {

    @Autowired
    private IChatContentService chatContentService;

    @ApiOperation(value = "分页查询")
    @GetMapping("/list")
    public RespPageBean getAllChatContent(@RequestParam(defaultValue = "1") Integer currentPage,
                            @RequestParam(defaultValue = "10") Integer size) {
        return chatContentService.getAllChatContent(currentPage, size);
    }

    @ApiOperation(value = "添加")
    @PostMapping("/")
    public RespBean addChatContent(@RequestBody ChatContent chatContent) {
        if(chatContentService.save(chatContent)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "修改")
    @PutMapping("/")
    public RespBean updateChatContent(@RequestBody ChatContent chatContent) {
        if(chatContentService.updateById(chatContent)) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public RespBean deleteChatContentById(@PathVariable Integer id) {
        if(chatContentService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("/")
    public RespBean deleteChatContentByIds(Integer[] ids) {
        if(chatContentService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}