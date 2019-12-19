package com.smart.service;

import com.smart.pojo.ContextualModel;
import com.smart.pojo.Equipment;

import java.util.List;

public interface EquipmentService {
    //查询所有设备信息
    List<Equipment> queryEquipments();

    //添加设备
    String addEquipment(Boolean flag);

    //根据id查询设备信息
    Equipment findById(Long id);

    //修改设备
    void alertEquipment(Long id,String eName,String eModel);

    //删除设备
    void deleteById(Long id);

    //开启继电器
    void relayOpen(Equipment equipment );

    //关闭继电器
    void relayClose(Equipment equipment);

    //关闭所有设备
    void closeAll();
    //查询所有模式列表
    List<ContextualModel> findAll();

    //打开指定的模式
    void openModel(Long id);

    //关闭莫斯
    void closeModel();
}
