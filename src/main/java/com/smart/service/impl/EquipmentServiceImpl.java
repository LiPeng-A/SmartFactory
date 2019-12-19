package com.smart.service.impl;

import com.pi4j.io.gpio.*;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.smart.config.I2cRelayProperties;
import com.smart.config.I2cSensorProperties;
import com.smart.enums.ExceptionEnum;
import com.smart.exception.FactoryException;
import com.smart.mapper.ContextualModelMapper;
import com.smart.mapper.EquipmentMapper;
import com.smart.pojo.ContextualModel;
import com.smart.pojo.Equipment;
import com.smart.service.EquipmentService;
import com.smart.utils.DateUtils;
import com.smart.utils.GpioUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@EnableConfigurationProperties({I2cRelayProperties.class, I2cSensorProperties.class})
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Autowired
    private I2cRelayProperties properties;
    @Autowired
    private ContextualModelMapper contextualModelMapper;
    @Autowired
    private I2cSensorProperties sensorProperties;


    /**
     * 查询所有设备
     *
     * @return
     */
    @Override
    public List<Equipment> queryEquipments() {
        List<Equipment> equipmentList = equipmentMapper.selectAll();
        if (CollectionUtils.isEmpty(equipmentList)) {
            throw new FactoryException(ExceptionEnum.EQUIPMENT_NOT_FOUND);
        }

        return equipmentList;

    }

    /**
     * 添加外接设备
     */
    @Transactional
    @Override
    public String addEquipment(Boolean flag) {

        try {
            while (flag) {
                I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
                I2CDevice device = i2c.getDevice(sensorProperties.getAddr());
                if (device.read(sensorProperties.getAddr()) < 0) {
                    return "外部开发板初始化错误";
                }
                if ((device.read(sensorProperties.getStatus_reg()) & 0x01) == 0x01) {
                    return "外部温度超过传感器温度上限";
                } else if ((device.read(sensorProperties.getStatus_reg()) & 0x02) == 0x02) {
                    return "无外部设备接入";
                } else {
                    return device.read(sensorProperties.getTemp_reg()) + "℃";
                }
            }
        } catch (I2CFactory.UnsupportedBusNumberException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Equipment findById(Long id) {
        Equipment equipment = equipmentMapper.selectByPrimaryKey(id);
        if (equipment == null) {
            throw new FactoryException(ExceptionEnum.EQUIPMENT_NOT_FOUND);
        }
        return equipment;
    }

    @Override
    public void alertEquipment(Long id, String eName, String eModel) {
        Equipment equipment = new Equipment();
        equipment.setEqu_name(eName);
        equipment.setModel(eModel);
        equipment.setId(id);
        int i = equipmentMapper.updateByPrimaryKeySelective(equipment);
        if (i != 1) {
            throw new FactoryException(ExceptionEnum.UPDATE_EQUIPMENT_ERROR);
        }
    }

    @Override
    public void deleteById(Long id) {
        int i = equipmentMapper.deleteByPrimaryKey(id);
        if (i != 1) {
            throw new FactoryException(ExceptionEnum.DELETE_EQUIPMENT_ERROR);
        }
    }

    @Transactional
    @Override
    public void relayOpen(Equipment equipment) {
        equipment.setStatus(1);
        //获取I2c线路实例
        try {
            I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
            //根据地址获取继电器
            I2CDevice device = i2c.getDevice(properties.getAddr());
            if (device.read(properties.getAddr()) < 0) { //判断板子是否初始化成功
                throw new FactoryException(ExceptionEnum.BOARD_INIT_FAIL);
            }
            switch (equipment.getRelay_no()) {
                case 1:
                    device.write((byte) properties.getRelay_1(), (byte) properties.getRelay_on());
                    int i = equipmentMapper.updateByPrimaryKeySelective(equipment);
                    if (i != 1) {
                        throw new FactoryException(ExceptionEnum.UPDATE_EQUIPMENT_ERROR);
                    }
                    break;
                case 2:
                    device.write((byte) properties.getRelay_2(), (byte) properties.getRelay_on());
                    int j = equipmentMapper.updateByPrimaryKeySelective(equipment);
                    if (j != 1) {
                        throw new FactoryException(ExceptionEnum.UPDATE_EQUIPMENT_ERROR);
                    }
                    break;
                case 3:
                    device.write((byte) properties.getRelay_3(), (byte) properties.getRelay_on());
                    int c = equipmentMapper.updateByPrimaryKeySelective(equipment);
                    if (c != 1) {
                        throw new FactoryException(ExceptionEnum.UPDATE_EQUIPMENT_ERROR);
                    }
                    break;
                case 4:
                    device.write((byte) properties.getRelay_4(), (byte) properties.getRelay_on());
                    int d = equipmentMapper.updateByPrimaryKeySelective(equipment);
                    if (d != 1) {
                        throw new FactoryException(ExceptionEnum.UPDATE_EQUIPMENT_ERROR);
                    }
                    break;
            }
        } catch (I2CFactory.UnsupportedBusNumberException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Override
    public void relayClose(Equipment equipment) {
        equipment.setStatus(0);
        equipment.setEnd_time(DateUtils.getCurrentTime());
        //获取I2c线路实例
        try {
            I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
            //根据地址获取继电器
            I2CDevice device = i2c.getDevice(properties.getAddr());
            if (device.read(properties.getAddr()) < 0) { //判断板子是否初始化成功
                throw new FactoryException(ExceptionEnum.BOARD_INIT_FAIL);
            }
            switch (equipment.getRelay_no()) {
                case 1:
                    device.write((byte) properties.getRelay_1(), (byte) properties.getRelay_off());
                    int i = equipmentMapper.updateByPrimaryKeySelective(equipment);
                    if (i != 1) {
                        throw new FactoryException(ExceptionEnum.UPDATE_EQUIPMENT_ERROR);
                    }
                    break;
                case 2:
                    device.write((byte) properties.getRelay_2(), (byte) properties.getRelay_off());
                    int j = equipmentMapper.updateByPrimaryKeySelective(equipment);
                    if (j != 1) {
                        throw new FactoryException(ExceptionEnum.UPDATE_EQUIPMENT_ERROR);
                    }
                    break;
                case 3:
                    device.write((byte) properties.getRelay_3(), (byte) properties.getRelay_off());
                    int c = equipmentMapper.updateByPrimaryKeySelective(equipment);
                    if (c != 1) {
                        throw new FactoryException(ExceptionEnum.UPDATE_EQUIPMENT_ERROR);
                    }
                    break;
                case 4:
                    device.write((byte) properties.getRelay_4(), (byte) properties.getRelay_off());
                    int d = equipmentMapper.updateByPrimaryKeySelective(equipment);
                    if (d != 1) {
                        throw new FactoryException(ExceptionEnum.UPDATE_EQUIPMENT_ERROR);
                    }
                    break;
            }
        } catch (I2CFactory.UnsupportedBusNumberException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeAll() {
        final GpioController gpioController = GpioUtils.getGpioController();
        final GpioPinDigitalOutput buzzer = GpioUtils.getBuzzer();
        //根据地址获取继电器
        try {
            I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
            I2CDevice device = i2c.getDevice(properties.getAddr());
            if (device.read(properties.getAddr()) < 0) { //判断板子是否初始化成功
                throw new FactoryException(ExceptionEnum.BOARD_INIT_FAIL);
            }
            //关闭所有设备
            device.write((byte) properties.getRelay_1(), (byte) properties.getRelay_off());
            device.write((byte) properties.getRelay_2(), (byte) properties.getRelay_off());
            device.write((byte) properties.getRelay_3(), (byte) properties.getRelay_off());
            device.write((byte) properties.getRelay_4(), (byte) properties.getRelay_off());
            //关闭蜂鸣传感器
            buzzer.high();
            //将数据库中的状态置为0
            equipmentMapper.updateStatus();
            buzzer.setShutdownOptions(true, PinState.HIGH, PinPullResistance.OFF);
            gpioController.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (I2CFactory.UnsupportedBusNumberException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<ContextualModel> findAll() {
        List<ContextualModel> models = contextualModelMapper.selectAll();
        if (CollectionUtils.isEmpty(models)) {
            throw new FactoryException(ExceptionEnum.CONTEXTUAL_MODEL_NOT_FOUND);
        }

        return models;
    }


    @Override
    @Transactional
    public void openModel(Long id) {
        ContextualModel contextualModel = contextualModelMapper.selectByPrimaryKey(id);
        if (contextualModel == null) {
            throw new FactoryException(ExceptionEnum.CONTEXTUAL_MODEL_NOT_FOUND);
        }
        //判断是哪个模式
        switch (Integer.parseInt(id.toString())) {
            case 1001://散热模式
                contextualModel.setStatus(1);
                contextualModelMapper.updateByPrimaryKeySelective(contextualModel);
                //开启一号风扇
                Equipment equipment = equipmentMapper.selectOne(new Equipment(1));
                relayOpen(equipment);
                //开启四号灯泡
                Equipment equipment1 = equipmentMapper.selectOne(new Equipment(4));
                relayOpen(equipment1);
                break;
            case 1002: //跑马灯模式
                contextualModel.setStatus(1);
                contextualModelMapper.updateByPrimaryKeySelective(contextualModel);
                int i = 0;
                while (i < 3) {
                    try {
                        //开启二号灯
                        Equipment equipment2 = equipmentMapper.selectOne(new Equipment(2));
                        relayOpen(equipment2);
                        Thread.sleep(1000);
                        //开启三号灯
                        Equipment equipment3 = equipmentMapper.selectOne(new Equipment(3));
                        relayOpen(equipment3);
                        Thread.sleep(1000);
                        //关闭二号灯
                        relayClose(equipment2);
                        Thread.sleep(1000);
                        //关闭三号灯
                        relayClose(equipment3);
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            case 1003: //灯光全开模式
                contextualModel.setStatus(1);
                contextualModelMapper.updateByPrimaryKeySelective(contextualModel);
                //开启二号灯
                Equipment equipment2 = equipmentMapper.selectOne(new Equipment(2));
                relayOpen(equipment2);
                //开启三号灯
                Equipment equipment3 = equipmentMapper.selectOne(new Equipment(3));
                relayOpen(equipment3);
                //开启四号灯
                Equipment equipment4 = equipmentMapper.selectOne(new Equipment(4));
                relayOpen(equipment4);
                break;
        }
    }


    @Override
    @Transactional
    public void closeModel() {
        //关闭所有设备
        closeAll();

        //改变模式状态
        contextualModelMapper.closeModel();
    }
}
