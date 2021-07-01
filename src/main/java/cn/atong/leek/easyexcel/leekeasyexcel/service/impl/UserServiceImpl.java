package cn.atong.leek.easyexcel.leekeasyexcel.service.impl;

import cn.atong.leek.easyexcel.leekeasyexcel.domain.entity.User;
import cn.atong.leek.easyexcel.leekeasyexcel.domain.excel.UserTemplate;
import cn.atong.leek.easyexcel.leekeasyexcel.mapper.UserMapper;
import cn.atong.leek.easyexcel.leekeasyexcel.service.UserService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: leek-easyexcel
 * @description: User Service
 * @author: atong
 * @create: 2021-06-30 10:04
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
        User user = userMapper.selectById(id);
        return user;
    }

    @Override
    public Integer saveUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public Integer batchSaveUser(List<User> users) {
        return null;
    }

    @Override
    public Integer importDataProcess(List<UserTemplate> userTemplates) {
        if (CollectionUtils.isEmpty(userTemplates)) {
            return null;
        }
        System.out.println("userTemplate = " + JSON.toJSONString(userTemplates));
        System.out.println("处理数据");
        return null;
    }
}
