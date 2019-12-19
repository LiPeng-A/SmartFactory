package com.smart.web;

import com.smart.pojo.Role;
import com.smart.pojo.UserInfo;
import com.smart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户集合
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<UserInfo>> findAll(){

        return ResponseEntity.ok(userService.findAll());
    }

    /**
     * 创建用户
     * @param info
     * @return
     */
    @PostMapping("addUser")
    public ResponseEntity<Void> addUser(@RequestBody UserInfo info){
        userService.addUser(info);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @RequestMapping("del/{id}")
    public ResponseEntity<Void> deleteUserByID (@PathVariable("id")Long id){
        userService.deleteUserByID(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据id查询用户的详细信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("user_show/{id}")
    public String  findUserById(@PathVariable("id")Long id,Model model){
        UserInfo info=userService.findUserById(id);
        model.addAttribute("info",info);
        return "user_show";
    }

    /**
     * 根据id查询用户的角色信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("role/{id}")
    public String findUserByRole(@PathVariable("id") Long id, Model model)
    {
        UserInfo info=userService.findUserByRole(id);
        model.addAttribute("info",info);
        return "user_role";
    }



}
