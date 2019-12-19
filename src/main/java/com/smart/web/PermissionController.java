package com.smart.web;

import com.smart.pojo.Permission;
import com.smart.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    /**
     * 查询所有权限信息
     * @return
     */
    @RequestMapping("list")
    public ResponseEntity<List<Permission>> findAll(){
        return ResponseEntity.ok(permissionService.findAll());
    }

    /**
     * 添加权限信息
     * @param permission
     * @return
     */
    @PostMapping("/addPermission")
    public ResponseEntity<Void> addPermission(@RequestBody Permission permission)
    {
        permissionService.addPermission(permission);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据id删除指定权限信息
     * @param id
     * @return
     */
    @DeleteMapping("del/{id}")
    public ResponseEntity<Void> deletePermissionById(@PathVariable("id")Long id)
    {
        permissionService.deletePermissionById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
