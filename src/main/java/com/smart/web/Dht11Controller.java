package com.smart.web;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.serial.*;
import com.pi4j.platform.Platform;
import com.pi4j.platform.PlatformAlreadyAssignedException;
import com.pi4j.platform.PlatformManager;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;
import com.pi4j.util.ConsoleColor;
import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.I2C;
import com.smart.config.I2cRelayProperties;
import com.smart.config.I2cSensorProperties;
import com.smart.utils.BytesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Controller
@EnableConfigurationProperties({I2cRelayProperties.class, I2cSensorProperties.class})
public class Dht11Controller {

    @Autowired
    private I2cRelayProperties properties;

    @Autowired
    private I2cSensorProperties sensorProperties;

    @RequestMapping("/init")
    @ResponseBody
    public void initRelay() {

        try {
            final Console console = new Console();
            //获取I2c线路实例
            I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
            //根据地址获取继电器
            I2CDevice device = i2c.getDevice(properties.getAddr());
            console.println("打开继电器");
            //打开第一个继电器
            device.write((byte) properties.getRelay_1(), (byte) properties.getRelay_on());
            Thread.sleep(5000);
            device.write((byte) properties.getRelay_1(), (byte) properties.getRelay_off());
            //关闭第一个继电器
            console.println("关闭继电器");
        } catch (I2CFactory.UnsupportedBusNumberException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 蜂鸣传感器
     */
    @RequestMapping("buzzer")
    @ResponseBody
    public void initDht11() {
        GpioController gpio = GpioFactory.getInstance();
        GpioPinDigitalOutput buzzer = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "buzzer", PinState.HIGH);
        try {
            System.out.println("开始");
            buzzer.low();
            Thread.sleep(5000);
            buzzer.high();
            Pin pin = CommandArgumentParser.getPin(RaspiPin.class, RaspiPin.GPIO_07);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("sensor")
    @ResponseBody
    public void initSensor() {

        final Console console = new Console();
        try {
            I2CBus i2c = I2CFactory.getInstance(I2CBus.BUS_1);
            I2CDevice device = i2c.getDevice(sensorProperties.getAddr());
            while (true) {
                if (device.read(sensorProperties.getAddr()) < 0) {
                    console.println("外部板无法初始化，请打开i2c重试", device.read(sensorProperties.getAddr()));
                } else {
                    console.println("外部板已经初始化");
                }
                if ((device.read(sensorProperties.getStatus_reg()) & 0x01) == 0x01) {
                    console.println("分机温度传感器超过温度");
                } else if ((device.read(sensorProperties.getStatus_reg()) & 0x02) == 0x02) {
                    console.println("无外部温度传感器");

                }else {
                    console.println("空气温度为:" + device.read(sensorProperties.getTemp_reg()) + "℃");

                }
                if ((device.read(sensorProperties.getStatus_reg()) & 0x04) == 0x04) {
                    console.println("光敏传感器超范围");
                } else if ((device.read(sensorProperties.getStatus_reg()) & 0x08) == 0x08) {
                    console.println("光敏传感器故障");
                } else {
                    console.println("当前亮度为" + (device.read(sensorProperties.getLight_reg_l()) | device.read(sensorProperties.getLight_reg_h() << 8)) + "lux");
                }
                if (device.read(sensorProperties.getBoard_sensor_error()) == 1) {
                    console.println("板载温度湿度传感器数据可能不是最新的");
                }
                console.println("温度为：" + device.read(sensorProperties.getBoard_temp()) + "℃");
                console.println("湿度为：" + device.read(sensorProperties.getBoard_humidity()) + "%");
                if (device.read(sensorProperties.getBmp280_status()) == 0) {
                    console.println("气压传感器的温度为:" + device.read(sensorProperties.getBmp280_temp_reg()) + "℃");
                    console.println("气压为：" + (device.read(sensorProperties.getBmp280_pressure_reg_h())
                            | device.read(sensorProperties.getBmp280_pressure_reg_m()) << 8 | device.read(sensorProperties.getBmp280_pressure_reg_h()) << 16) + "pa");
                } else {
                    console.println("车载气压计工作异常");
                }
                if (device.read(sensorProperties.getHuman_detect()) == 1) {
                    console.println("在5秒内监测到活体");
                } else {
                    console.println("没有发现活体");
                }
                Thread.sleep(3000);
            }

        } catch (I2CFactory.UnsupportedBusNumberException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
