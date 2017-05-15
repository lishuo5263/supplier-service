package com.ecochain.ledger.rest;

import com.ecochain.ledger.model.User;
import com.ecochain.ledger.service.UserService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Lisandro on 2017年5月15日16:00:16.
 */
@RestController
@RequestMapping(value = "/api/rest/")
@Api(value = "用户Service")
public class UserWebService {

    private Logger logger = Logger.getLogger(UserWebService.class);

    @Autowired
    private UserService userService;

    @PostMapping("/getUserInfo")
    public User getUserInfo() {
        User user = userService.getUserInfo();
        if(user!=null){
            System.out.println("user.getName():"+user.getName());
            logger.info("user.getAge():"+user.getAge());
        }
        return user;
    }

    @PostMapping("/getAllUserInfo")
    @ApiOperation(nickname = "用于测试getAllUserInfo1", value = "获取所有用户信息", notes = "获取所有用户信息！！")
    public List getAllUserInfo() {
        List<User> user = userService.getAllUserInfo();
        /*if(user!=null){
            System.out.println("user.ge tName():"+user.getName());
            logger.info("user.getAge():"+user.getAge());
        }*/
        return user;
    }

    @GetMapping("/message/{currentPage}")
    public String message(@PathVariable("currentPage") Integer currentPage, Model model) {
        if (currentPage != null) {
            PageHelper.startPage(currentPage,5);
            //PageHelper.startPage(currentPage, 10);
        }
        List<User> messages = userService.getAllUserInfo();
        /*model.addAttribute("messages", messages);
        return "message";*/
        return messages.toString();
    }
}
