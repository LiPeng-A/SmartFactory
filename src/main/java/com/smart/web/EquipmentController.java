package com.smart.web;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.smart.config.I2cRelayProperties;
import com.smart.pojo.Equipment;
import com.smart.service.EquipmentService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.jws.WebParam;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private EquipmentService equipmentService; //注入service



    /**
     * 查询所有设备
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Equipment>> queryEquipments(Model model) {
        List<Equipment> equipmentList = equipmentService.queryEquipments();
        return ResponseEntity.ok(equipmentList);
    }

    /**
     * 添加外接设备
     *
     * @return
     */
    @PostMapping("{flag}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> addEquipment(@PathVariable("flag") Boolean flag) {
        return ResponseEntity.ok(equipmentService.addEquipment(flag));
    }


    /**
     * 根据id查询设备信息
     *
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public String findById(Model model, @RequestParam("id") Long id) {
        Equipment equipment = equipmentService.findById(id);
        model.addAttribute("equipment", equipment);
        return "equ_update";
    }

    /**
     * 修改设备
     *
     * @param id
     * @param eName
     * @param eModel
     * @return
     */
    @PostMapping("/alterEquipment")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String alertEquipment(
            @RequestParam("id") Long id,
            @RequestParam("e_name") String eName,
            @RequestParam("e_model") String eModel
    ) {
        equipmentService.alertEquipment(id, eName, eModel);
        return "redirect:/equipment/list";
    }

    /**
     * 删除设备
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String deleteById(@RequestParam("id") Long id) {

        equipmentService.deleteById(id);
        return "redirect:/equipment/list";
    }

    /**
     * 开启继电器口
     * @param equipment
     * @return
     */
    @PostMapping("/relayOpen")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public String  relayOpen(@RequestBody Equipment equipment) {
        equipmentService.relayOpen(equipment);
        return "redirect:/equipment/list";
    }

    /**
     * 关闭继电器口
     * @param equipment
     * @return
     */
    @PostMapping("/relayClose")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public String relayClose(@RequestBody Equipment equipment){
        equipmentService.relayClose(equipment);
        return "redirect:/equipment/list";
    }


    /**
     * 关闭所有设备
     * @return
     */
    @PostMapping("close")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<Void> closeAll(){
        equipmentService.closeAll();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
