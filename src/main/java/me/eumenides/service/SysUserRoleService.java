package me.eumenides.service;

import me.eumenides.entity.SysUserRole;

import java.util.List;

/**
 * Created by Eumenides on 2017/10/18.
 */
public interface SysUserRoleService {
    List<SysUserRole> findByConditions(SysUserRole sysUserRole);
}
