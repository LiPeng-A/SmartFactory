package com.smart.service;

import com.smart.pojo.Sensor;
import com.smart.pojo.Threshold;
import com.smart.view.SensorInfoResult;
import com.smart.view.SensorDataResult;

public interface SensorService {
    //采集数据
    Sensor gatherData(Integer radio,Threshold threshold);


    //查询采集到的数据
    SensorDataResult findAll();

    //查询获得数据的最大值
    SensorInfoResult findMax();

    //删除表中的数据
    void deleteAny(Boolean flag);
}
