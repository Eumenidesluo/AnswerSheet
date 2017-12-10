package me.eumenides.service.impl;

import me.eumenides.dao.SysUserRoleMapper;
import me.eumenides.entity.SysUserRole;
import me.eumenides.entity.SysUserRoleExample;
import me.eumenides.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Eumenides on 2017/10/18.
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {
    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Override
    public List<SysUserRole> findByConditions(SysUserRole sysUserRole) {
        if (sysUserRole == null)
            return null;
        SysUserRoleExample example = new SysUserRoleExample();
        buildExample(example,sysUserRole);
        return userRoleMapper.selectByExample(example);
    }

    private void buildExample(SysUserRoleExample example, SysUserRole entity) {
        SysUserRoleExample.Criteria criteria = example.createCriteria();
        if (entity.getRoleId() != null) {
            criteria.andRoleIdEqualTo(entity.getRoleId());
        }
        if (entity.getUserId() != null) {
            criteria.andUserIdEqualTo(entity.getUserId());
        }
    }
}
