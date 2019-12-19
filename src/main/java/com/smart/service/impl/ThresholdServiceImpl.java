package com.smart.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.enums.ExceptionEnum;
import com.smart.exception.FactoryException;
import com.smart.mapper.ThresholdMapper;
import com.smart.pojo.Threshold;
import com.smart.service.ThresholdService;
import com.smart.utils.DateUtils;
import com.smart.view.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ThresholdServiceImpl implements ThresholdService {

    @Autowired
    private ThresholdMapper thresholdMapper;
    @Override
    public void settingThreshold(Threshold threshold) {
        threshold.setId(System.currentTimeMillis());
        threshold.setTime(DateUtils.getCurrentTime());
        int i = thresholdMapper.insertSelective(threshold);
        if(i!=1)
        {
            throw new FactoryException(ExceptionEnum.SAVE_THRESHOLD_ERROR);
        }
    }

    @Override
    public PageResult findAll(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Threshold> thresholds = thresholdMapper.selectAll();
        if (CollectionUtils.isEmpty(thresholds)) {
            throw new FactoryException(ExceptionEnum.THRESHOLD_NOT_FOUND);
        }
        PageInfo info =new PageInfo(thresholds);
        //封装数据并返回
        //总页数
        long l = info.getTotal() / size;
        if(info.getTotal() % size!=0)
        {
            l++;
        }

        return new PageResult(info.getTotal(),info.getList(),l);
    }

    @Override
    public void deleteThresholdById(Long id) {
        int i = thresholdMapper.deleteByPrimaryKey(id);
        if(i!=1)
        {
            throw new FactoryException(ExceptionEnum.THRESHOLD_DELETE_FAIL);
        }
    }

    @Override
    public Threshold findThresholdById(Long id) {
        Threshold threshold = thresholdMapper.selectByPrimaryKey(id);
        if(threshold==null)
        {
            throw new FactoryException(ExceptionEnum.THRESHOLD_NOT_FOUND);
        }
        return threshold;
    }
}
