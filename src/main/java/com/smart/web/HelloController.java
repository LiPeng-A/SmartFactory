package com.smart.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class HelloController {

    @RequestMapping("index")
    public String index() {

        return "index";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping("gather")
    public String gather() {

        return "gather_data";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping("equ")
    public String equ() {

        return "equ_manage";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping("history_data")
    public String history() {
        return "historical_data";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping("data_analyze")
    public String analyze() {
        return "data_analyze";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping("threshold")
    public String threshold() {
        return "threshold_setting";
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping("contextual_model")
    public String model() {
        return "contextual_model";
    }

    @RequestMapping("login")
    public String userLogin() {
        return "login";
    }

    @RequestMapping("loginError")
    public String loginError() {
        return "loginError";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("user_list")
    public String userList() {
        return "user_list";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("user_add")
    public String userAdd() {
        return "user_add";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("role_list")
    public String roleList() {
        return "role_list";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("role_permission")
    public String rolePermission() {
        return "role_permission";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("role_add")
    public String role_add() {
        return "role_add";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("permission_list")
    public String permission_list() {
        return "permission_list";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("permission_add")
    public String permission_add() {
        return "permission_add";
    }

    @RequestMapping("/403")
    public String permissionAuth(){
        return "403";
    }

    @GetMapping("hello")
    public ResponseEntity<Void> helloSession(HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            session.setAttribute("name",currentUserName);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
