package com.smart.view;

import lombok.Data;

@Data
public class SensorInfoResult {
    private Integer maxTemp;
    private Integer maxHum;
    private Integer maxLight;
    private Integer maxAirPressure;
    private Integer Count;

}
