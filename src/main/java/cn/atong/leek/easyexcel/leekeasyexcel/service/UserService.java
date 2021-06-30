package cn.atong.leek.easyexcel.leekeasyexcel.service;

import cn.atong.leek.easyexcel.leekeasyexcel.domain.entity.User;
import cn.atong.leek.easyexcel.leekeasyexcel.domain.excel.UserTemplate;

import java.util.List;

/**
 * @program: leek-easyexcel
 * @description:
 * @author: atong
 * @create: 2021-06-30 10:11
 */
public interface UserService {
    User getUserById(Long id);
    Integer saveUser(User user);
    Integer batchSaveUser(List<User>users);

    Integer importDataProcess(List<UserTemplate> userTemplates);
}
