package com.smart.service.impl;

import com.pi4j.io.gpio.*;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.smart.config.I2cSensorProperties;
import com.smart.enums.ExceptionEnum;
import com.smart.exception.FactoryException;
import com.smart.mapper.*;
import com.smart.pojo.*;
import com.smart.service.EquipmentService;
import com.smart.service.SensorService;
import com.smart.utils.DateUtils;
import com.smart.utils.GpioUtils;
import com.smart.view.SensorInfoResult;
import com.smart.view.SensorDataResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@EnableConfigurationProperties(I2cSensorProperties.class)
@Slf4j
public class SensorServiceImpl implements SensorService {

    @Autowired
    private I2cSensorProperties sensorProperties;

    @Autowired
    private SensorMapper sensorMapper;

    @Autowired
    private SensorCopyMapper copyMapper;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private EquipmentMapper equipmentMapper;


    @Override
    @Transactional
    public Sensor gatherData(Integer radio, Threshold threshold) {
        final GpioController gpioController = GpioUtils.getGpioController();
        final GpioPinDigitalOutput buzzer = GpioUtils.getBuzzer();
        try {
            I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
            I2CDevice device = i2c.getDevice(sensorProperties.getAddr());
            Sensor sensor = new Sensor();
            SensorCopy copy = new SensorCopy();
            sensor.setId(System.currentTimeMillis());
            copy.setId(sensor.getId());
            if (device.read(sensorProperties.getAddr()) < 0) { //判断板子是否初始化成功
                throw new FactoryException(ExceptionEnum.BOARD_INIT_FAIL);
            }
            System.out.println("初始化完成");
            if ((device.read(sensorProperties.getStatus_reg()) & 0x04) == 0x04) { //判断光照强度是否超出范围
                throw new FactoryException(ExceptionEnum.LIGHT_OUT_OF_RANGE);
            } else if ((device.read(sensorProperties.getStatus_reg()) & 0x08) == 0x08) { //判断光敏传感器是否故障
                throw new FactoryException(ExceptionEnum.LIGHT_SENSOR_FAULT);
            } else {
                //光照强度
                Integer light = device.read(sensorProperties.getLight_reg_l()) | device.read(sensorProperties.getLight_reg_h() << 8);
                sensor.setLight(light);
                copy.setLight(light);
                System.out.println("光强：" + light);
            }
            if (device.read(sensorProperties.getBoard_sensor_error()) == 1) {
                throw new FactoryException(ExceptionEnum.TEMP_HUM_NOT_FOUND);
            } else {
                //温度
                Integer temp = device.read(sensorProperties.getBoard_temp());
                //湿度
                Integer hum = device.read(sensorProperties.getBoard_humidity());
                sensor.setTemperature(temp);
                sensor.setHumidity(hum);
                copy.setTemperature(temp);
                copy.setHumidity(hum);
                System.out.println("温度：" + temp + ",湿度：" + hum);
            }
            if (device.read(sensorProperties.getBmp280_status()) == 0) {
                //气压
                Integer airPress = device.read(sensorProperties.getBmp280_pressure_reg_h())
                        | device.read(sensorProperties.getBmp280_pressure_reg_m()) << 8 | device.read(sensorProperties.getBmp280_pressure_reg_h()) << 16;
                sensor.setAir_pressure(airPress);
                copy.setAir_pressure(airPress);
                System.out.println("气压:" + airPress);
            } else {
                throw new FactoryException(ExceptionEnum.AIR_PRESSURE_EXCEPTION);
            }
            if (device.read(sensorProperties.getHuman_detect()) == 1) {
                sensor.setIs_human(1);
                copy.setIs_human(1);
                System.out.println("有人");
            } else {
                sensor.setIs_human(0);
                copy.setIs_human(0);
                System.out.println("没人");
            }
            sensor.setNow_time(DateUtils.getCurrentTime());
            copy.setNow_time(sensor.getNow_time());
            sensorMapper.insertSelective(sensor);
            copyMapper.insertSelective(copy);
            //进行模式选择并进行操作
            if (radio == 1) {
                //智能模式
                if (threshold != null) {

                    //温度智能设备控制
                    if (sensor.getTemperature() > threshold.getTemp_threshold()) {
                        //蜂鸣传感器开启
                        buzzer.low();
                        //查询要打开的继电器接口
                        Equipment equipment = equipmentMapper.selectOne(new Equipment(1));
                        //打开开关 风扇
                        equipmentService.relayOpen(equipment);
                    } else {
                        //关闭蜂鸣传感器
                        buzzer.high();
                        //查询要关闭的继电器接口
                        Equipment equipment = equipmentMapper.selectOne(new Equipment(1));
                        equipmentService.relayClose(equipment);
                    }
                    //湿度智能设备控制
                    if (sensor.getHumidity() < threshold.getHum_threshold()) {
                        //查询要打开的继电器接口
                        Equipment equipment = equipmentMapper.selectOne(new Equipment(2));
                        //打开开关
                        equipmentService.relayOpen(equipment);
                    } else {
                        //查询要关闭的继电器接口
                        Equipment equipment = equipmentMapper.selectOne(new Equipment(2));
                        equipmentService.relayClose(equipment);
                    }
                    //光强智能设备控制
                    if (sensor.getLight() < threshold.getLight_threshold()) {
                        //查询要打开的继电器接口
                        Equipment equipment = equipmentMapper.selectOne(new Equipment(3));
                        //打开开关
                        equipmentService.relayOpen(equipment);
                    } else {
                        //查询要关闭的继电器接口
                        Equipment equipment = equipmentMapper.selectOne(new Equipment(3));
                        equipmentService.relayClose(equipment);
                    }
                    //活体监测智能设备控制
                    if (sensor.getIs_human() == 1) {
                        //查询要打开的继电器接口
                        Equipment equipment = equipmentMapper.selectOne(new Equipment(4));
                        //打开开关
                        equipmentService.relayOpen(equipment);
                    } else {
                        //查询要关闭的继电器接口
                        Equipment equipment = equipmentMapper.selectOne(new Equipment(4));
                        equipmentService.relayClose(equipment);
                    }
                }
            }
            gpioController.shutdown();
            return sensor;
        } catch (I2CFactory.UnsupportedBusNumberException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SensorDataResult findAll() {
        List<Sensor> sensors = sensorMapper.getSensor();
        //将每个采集到的值都封装成要的类型
        List<Integer> temperatures = sensors.stream().map(Sensor::getTemperature).collect(Collectors.toList());
        List<String> now_times = sensors.stream().map(Sensor::getNow_time).collect(Collectors.toList());
        List<Integer> hums = sensors.stream().map(Sensor::getHumidity).collect(Collectors.toList());
        List<Integer> air_Press = sensors.stream().map(Sensor::getAir_pressure).collect(Collectors.toList());
        List<Integer> humans = sensors.stream().map(Sensor::getIs_human).collect(Collectors.toList());
        List<Integer> lights = sensors.stream().map(Sensor::getLight).collect(Collectors.toList());
        //将封装好的值拼装好返回页面
        SensorDataResult dataResult = new SensorDataResult();
        dataResult.setTempers(temperatures);
        dataResult.setNow_times(now_times);
        dataResult.setHums(hums);
        dataResult.setAirPress(air_Press);
        dataResult.setIs_humans(humans);
        dataResult.setLights(lights);
        //获取光强的平均值
        Integer lightAvg = sensorMapper.getLightAvg();
        dataResult.setLightAvg(lightAvg);
        Integer pressAvg = sensorMapper.getPressAvg();
        dataResult.setPressAvg(pressAvg);
        return dataResult;
    }

    @Override
    public SensorInfoResult findMax() {
        //获取所采集数据中的最大值
        Integer maxTemp = sensorMapper.getMaxTemp();
        Integer maxHum = sensorMapper.getMaxHum();
        Integer maxLight = sensorMapper.getMaxLight();
        Integer maxAirPress = sensorMapper.getMaxAirPress();
        SensorInfoResult infoResult = new SensorInfoResult();
        //拼装属性
        infoResult.setMaxTemp(maxTemp);
        infoResult.setMaxHum(maxHum);
        infoResult.setMaxLight(maxLight);
        infoResult.setMaxAirPressure(maxAirPress);
        //获取所采集数据的次数
        Integer count = sensorMapper.getCount();
        infoResult.setCount(count);
        return infoResult;
    }

    @Override
    @Transactional
    public void deleteAny(Boolean flag) {
        if (flag) {
            sensorMapper.deleteAnyData(); //删除所有数据
            copyMapper.deleteCopy();
            return;
        }
        //只删除当前采集的数据
        sensorMapper.deleteAnyData();
    }
}
