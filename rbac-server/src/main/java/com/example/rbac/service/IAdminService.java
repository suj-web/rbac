package com.example.rbac.service;

import com.example.rbac.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.rbac.pojo.RespBean;
import com.example.rbac.pojo.Role;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author suj
 * @since 2022-01-07
 */
public interface IAdminService extends IService<Admin> {

    /**
     * 根据用户名获取管理员实体
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);

    /**
     * 根据管理员id获取对应的角色
     * @param adminId
     * @return
     */
    List<Role> getRolesByAdminId(Integer adminId);

    /**
     * 更新管理员密码
     * @param oldPass
     * @param pass
     * @param adminId
     * @return
     */
    RespBean updateAdminPassword(String oldPass, String pass, Integer adminId);

    /**
     * 更新管理员头像
     * @param url
     * @param id
     * @param authentication
     * @return
     */
    RespBean updateAdminUserFace(String url, Integer id, Authentication authentication);

    /**
     * 获取所有管理员
     * @param keywords
     * @return
     */
    List<Admin> getAllAdmins(String keywords);

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    RespBean updateAdminRole(Integer adminId, Integer[] rids);
}
