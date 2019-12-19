package com.smart.service;

import com.smart.pojo.SensorDataRequest;
import com.smart.view.PageResult;
import com.smart.view.SensorCopyResult;


public interface SensorCopyService {
    //分页查询历史数据
    PageResult querySensorDataList(SensorDataRequest request);

    //查询所有信息
    SensorCopyResult queryList();
}
