package com.smart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "docker.sensor")
public class I2cSensorProperties {
    private int addr;
    private int temp_reg;
    private int light_reg_l;
    private int light_reg_h;
    private int status_reg;
    private int board_temp;
    private int board_humidity;
    private int board_sensor_error;
    private int bmp280_temp_reg;
    private int bmp280_pressure_reg_l;
    private int bmp280_pressure_reg_m;
    private int bmp280_pressure_reg_h;
    private int bmp280_status;
    private int human_detect;
}
