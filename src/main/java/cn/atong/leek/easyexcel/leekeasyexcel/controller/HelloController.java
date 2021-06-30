package cn.atong.leek.easyexcel.leekeasyexcel.controller;

import cn.atong.leek.easyexcel.leekeasyexcel.domain.entity.User;
import cn.atong.leek.easyexcel.leekeasyexcel.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: leek-easyexcel
 * @description: Hello Controller
 * @author: atong
 * @create: 2021-06-30 09:52
 */
@Slf4j
@RestController
public class HelloController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user;
    }
}
