package com.smart.web;

import com.smart.pojo.Threshold;
import com.smart.service.ThresholdService;
import com.smart.view.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("threshold")
public class ThresholdController {


    @Autowired
    private ThresholdService thresholdService;

    /**
     * 保存阈值信息
     * @param threshold
     * @return
     */
    @PostMapping("setting")
    public ResponseEntity<Void> settingThreshold(@RequestBody Threshold threshold){
        thresholdService.settingThreshold(threshold);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 查询所有的阈值信息
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<PageResult> findAll(@RequestParam("pageNum")Integer page,
                                              @RequestParam("pageSize")Integer size){
        return ResponseEntity.ok(thresholdService.findAll(page,size));
    }
    /**
     * 根据id删除阈值信息
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteThresholdById(@PathVariable("id")Long id){
        thresholdService.deleteThresholdById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 根据id查询阈值信息
     * @param id
     * @return
     */
    @GetMapping("findById/{id}")
    public ResponseEntity<Threshold> findThresholdById(@PathVariable("id")Long id){
        return ResponseEntity.ok(thresholdService.findThresholdById(id));
    }
}
