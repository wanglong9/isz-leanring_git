package com.e7code.crud.controller;

import com.e7code.common.api.bean.*;
import com.e7code.common.mvc.bean.HttpQueryParams;
import com.e7code.common.mvc.controller.BaseController;
import com.e7code.crud.model.User;
import com.e7code.crud.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RequestMapping("user")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserManager userManager;

    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }

    @RequestMapping("/save")
    public Result save(User user){
        userManager.save(user);
        return Result.createSuccessResult(user);
    }

    @RequestMapping("/saveUserName")
    public Result saveUserName(User user){
        userManager.update4properties(user, new String[]{"username"});
        return Result.createSuccessResult(user);
    }

    @RequestMapping("/save4mark")
    public Result saveExcludeUserName(User user){
        userManager.update4ignoreProperties(user, new String[]{"username"});
        return Result.createSuccessResult(user);
    }

    @RequestMapping("/get")
    public Result get(Integer id){
        return Result.createSuccessResult(this.userManager.get(id));
    }

    @RequestMapping("/delete")
    public Result delete(Integer id){
        this.userManager.delete(id);
        return Result.createSuccessResult();
    }

    @RequestMapping("/query")
    public Result query(HttpQueryParams params) {
        return Result.createSuccessResult(userManager.queryPage(params.toModelQueryParams(User.class)));
    }

    @RequestMapping("/queryTest")
    public Result queryTest() {
        ModelQueryParams queryParams = new ModelQueryParams();
        queryParams.like("username", "ssr")
                .equal("locked", true)
                .greaterThan("orderNum", 10000)
                .lessThanEqual("money", 50.5)
                .lessThan("fee", 10.1)
                .equal("matchTypeId", MatchType.EQ)
                .equal("matchTypeName", MatchType.EQ)
                .like("comment", "ssr")
                .greaterThanEqual("birthday", new Date());

        return Result.createSuccessResult(userManager.queryPage(queryParams));
    }
}
