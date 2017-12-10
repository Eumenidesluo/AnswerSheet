package me.eumenides.service.impl;

import me.eumenides.dao.UserMapper;
import me.eumenides.entity.User;
import me.eumenides.entity.UserExample;
import me.eumenides.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by Eumenides on 2017/10/18.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findByConditions(User user) {
        if (user == null)
            return null;
        UserExample example = new UserExample();
        buildExample(example,user);
        return userMapper.selectByExample(example);
    }

    private void buildExample(UserExample example, User user) {
        UserExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(user.getUsername()))
            criteria.andUsernameEqualTo(user.getUsername());
        if (!StringUtils.isEmpty(user.getName()))
            criteria.andNameLike("%"+user.getName()+"%");
    }

    @Override
    public User findByUsername(String username) {
        User user=new User();
        user.setUsername(username);
        List<User> users=findByConditions(user);
        if (users==null||users.isEmpty())
            return null;
        return users.get(0);
    }

    @Override
    public User findByUserId(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public Integer insertNewUser(User user) {
        return userMapper.insertSelective(user);
    }
}
