package com.smart.web;

import com.smart.mapper.RoleMapper;
import com.smart.pojo.Permission;
import com.smart.pojo.Role;
import com.smart.service.RoleService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查询所有角色
     *
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<Role>> findAll() {
        return ResponseEntity.ok(roleService.findAll());
    }

    /**
     * 设置角色信息
     *
     * @param id
     * @return
     */
    @PostMapping("/setting/{id}/{info_id}")
    public ResponseEntity<Void> settingRole(@PathVariable("id") Long id,
                                            @PathVariable("info_id") Long u_id
    ) {
        roleService.settingRole(id, u_id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除角色
     *
     * @return
     */
    @DeleteMapping("/del/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable("id") Long role_id) {
        roleService.deleteRole(role_id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 角色详情页
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("show/{id}")
    public String showRole(@PathVariable("id") Long id, Model model) {
        Role role = roleService.findAllShow(id);
        model.addAttribute("role", role);
        return "role_show";
    }

    /**
     * 查询可以设置的权限信息
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("permission/{id}")
    public String findPermission(@PathVariable("id") Long id, Model model) {
        Role role = roleService.findPermission(id);
        model.addAttribute("role",role);
        return "role_permission";
    }

    /**
     * 设置角色的权限信息
     * @param per_id
     * @param role_id
     * @return
     */
    @RequestMapping("settingPermission/{id}/{role_id}")
    public ResponseEntity<Void> settingPermission(@PathVariable("id") Long per_id,@PathVariable("role_id")Long role_id)
    {
        roleService.settingPermission(per_id,role_id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除当前角色的权限
     * @param per_id
     * @param roleId
     * @return
     */
    @DeleteMapping("delPermission/{id}/{roleId}")
    public ResponseEntity<Void> delPermission(@PathVariable("id")Long per_id,@PathVariable("roleId")Long roleId){

        roleService.delPermission(per_id,roleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 添加角色信息
     * @param role
     * @return
     */
    @PostMapping("addRole")
    public ResponseEntity<Void> addRole(@RequestBody Role role){
        roleService.addRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
