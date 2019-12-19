package com.smart.web;

import com.smart.pojo.ContextualModel;
import com.smart.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/contextual")
public class ContextualModelController {

    @Autowired
    private EquipmentService service;


    /**
     * 查询所有模式列表
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<ContextualModel>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * 打开情景模式
     * @return
     */
    @PostMapping("open/{id}")
    public ResponseEntity<Void> openModel(@PathVariable("id")Long id){

        service.openModel(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping("close")
    public ResponseEntity<Void> closeModel(){
        service.closeModel();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
