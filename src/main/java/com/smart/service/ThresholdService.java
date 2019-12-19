package com.smart.service;

import com.smart.pojo.Threshold;
import com.smart.view.PageResult;

import java.util.List;

public interface ThresholdService {


    //保存阈值信息
    void settingThreshold(Threshold threshold);

    //查询所有的阈值信息
    PageResult findAll(Integer page, Integer size);

    //删除阈值信息
    void deleteThresholdById(Long id);

    //根据id查询阈值信息
    Threshold findThresholdById(Long id);
}
