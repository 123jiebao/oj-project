package com.lzy.ojuserservice.controller.inner;


import com.lzy.ojmodel.model.entity.User;
import com.lzy.ojuserservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/inner")
@Slf4j
public class UserInnerController{

    @Resource
    private UserService userService;

    @GetMapping("/get/id")
    public User getById(@RequestParam("userId") long userId) {
        return userService.getById(userId);
    }

    @GetMapping("/get/ids")
    public List<User> listByIds(@RequestParam("idList") Collection<Long> idList) {
        return userService.listByIds(idList);
    }

    @GetMapping("/get/login")
    public User getLoginUser(HttpServletRequest request){
        return userService.getLoginUser(request);
    }


}
