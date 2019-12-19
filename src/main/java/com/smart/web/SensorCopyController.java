package com.smart.web;

import com.smart.pojo.SensorCopy;
import com.smart.pojo.SensorDataRequest;
import com.smart.service.SensorCopyService;
import com.smart.view.PageResult;
import com.smart.view.SensorCopyResult;
import com.smart.view.SensorDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/sensorCopy")
public class SensorCopyController {

    @Autowired
    private SensorCopyService sensorCopyService;

    /**
     * 分页查询
     * @return
     */
    @PostMapping("/list")
    public ResponseEntity<PageResult> querySensorDataList(@RequestBody SensorDataRequest request){
        return ResponseEntity.ok(sensorCopyService.querySensorDataList(request));
    }

    /**
     * 查询所有信息 并封装
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<SensorCopyResult> queryList(){
        return ResponseEntity.ok(sensorCopyService.queryList());
    }

}
