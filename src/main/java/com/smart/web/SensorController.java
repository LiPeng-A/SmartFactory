package com.smart.web;

import com.smart.pojo.Sensor;
import com.smart.pojo.Threshold;
import com.smart.service.SensorService;
import com.smart.view.SensorInfoResult;
import com.smart.view.SensorDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("sensor")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    /**
     * 采集信息 并进行模式选择
     *
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("gather/{radio}")
    public ResponseEntity<Sensor> gatherData(@PathVariable("radio") Integer radio,
                                             @RequestParam("temp_threshold") String temp_threshold,
                                             @RequestParam("hum_threshold") String hum_threshold,
                                             @RequestParam("light_threshold") String light_threshold) {
        Threshold threshold=new Threshold();
        threshold.setTemp_threshold(Integer.parseInt(temp_threshold));
        threshold.setHum_threshold(Integer.parseInt(hum_threshold));
        threshold.setLight_threshold(Integer.parseInt(light_threshold));
        Sensor sensor = sensorService.gatherData(radio, threshold);
        return ResponseEntity.ok(sensor);
    }

    /**
     * 查询采集到的数据
     *
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<SensorDataResult> findAll() {
        return ResponseEntity.ok(sensorService.findAll());
    }

    /**
     * 查询采集数据的最大值
     *
     * @return
     */
    @GetMapping("dataMax")
    public ResponseEntity<SensorInfoResult> findMax() {
        return ResponseEntity.ok(sensorService.findMax());
    }

    /**
     * 删除所有的数据
     *
     * @param flag
     * @return
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("delete/{flag}")
    public ResponseEntity<Void> deleteAny(@PathVariable("flag") Boolean flag) {
        sensorService.deleteAny(flag);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
