package com.smart.view;

import lombok.Data;

import java.util.List;

@Data
public class SensorDataResult {

    private List<String> now_times;
    private List<Integer> tempers;
    private List<Integer> hums;
    private List<Integer> lights;
    private List<Integer> airPress;
    private List<Integer> is_humans;
    private Integer lightAvg;
    private Integer pressAvg;
}
