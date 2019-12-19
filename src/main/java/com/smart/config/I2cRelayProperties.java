package com.smart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("fa.i2c")
public class I2cRelayProperties {
    private int addr;
    private int relay_1;
    private int relay_2;
    private int relay_3;
    private int relay_4;
    private int relay_on;
    private int relay_off;

}
