package com.example.rbac.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.rbac.service.IChatService;
import com.example.rbac.pojo.Chat;
import com.example.rbac.pojo.RespPageBean;
import java.util.Arrays;

/**
 * ChatController
 * @author suj
 * @since 2022-4-27
 */
@RestController
@RequestMapping("/chatController")
public class ChatController {

    @Autowired
    private IChatService chatService;

    @ApiOperation(value = "分页查询")
    @GetMapping("/list")
    public RespPageBean getAllChat(@RequestParam(defaultValue = "1") Integer currentPage,
                            @RequestParam(defaultValue = "10") Integer size) {
        return chatService.getAllChat(currentPage, size);
    }

    @ApiOperation(value = "添加")
    @PostMapping("/")
    public RespBean addChat(@RequestBody Chat chat) {
        if(chatService.save(chat)) {
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "修改")
    @PutMapping("/")
    public RespBean updateChat(@RequestBody Chat chat) {
        if(chatService.updateById(chat)) {
            return RespBean.success("修改成功");
        }
        return RespBean.error("修改失败");
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public RespBean deleteChatById(@PathVariable Integer id) {
        if(chatService.removeById(id)) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("/")
    public RespBean deleteChatByIds(Integer[] ids) {
        if(chatService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }
}