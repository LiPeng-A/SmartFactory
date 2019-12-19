package com.smart.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smart.enums.ExceptionEnum;
import com.smart.exception.FactoryException;
import com.smart.mapper.SensorCopyMapper;
import com.smart.pojo.SensorCopy;
import com.smart.pojo.SensorDataRequest;
import com.smart.service.SensorCopyService;
import com.smart.view.PageResult;
import com.smart.view.SensorCopyResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;


@Service
@Slf4j
public class SensorCopyServiceImpl implements SensorCopyService {

    @Autowired
    private SensorCopyMapper sensorCopyMapper;


    @Override
    public PageResult querySensorDataList(SensorDataRequest request) {
        System.out.println(request.getDate());

        //分页
        PageHelper.startPage(request.getPageNum(),request.getPageSize());
        //过滤
        Example example=new Example(SensorCopy.class);
        Example.Criteria criteria = example.createCriteria();
        //过滤时间
        if(StringUtils.isNotBlank(request.getDate()))
        {
            criteria.andLike("now_time","%"+request.getDate()+"%");
        }
        //过滤id
        if(StringUtils.isNotBlank(request.getId()))
        {
            criteria.andLike("id","%"+request.getId()+"%");
        }
        //默认排序
        example.setOrderByClause("now_time DESC");

        //查询
        List<SensorCopy> copyList = sensorCopyMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(copyList)) {
            throw new FactoryException(ExceptionEnum.HISTORY_DATA_NOT_FOUND);
        }
        PageInfo<SensorCopy> info=new PageInfo<>(copyList);
        //封装数据并返回
        //总页数
        long l = info.getTotal() / request.getPageSize();
        if(info.getTotal() % request.getPageSize()!=0)
        {
           l++;
        }
        return new PageResult(info.getTotal(),info.getList(),l);
    }

    @Override
    public SensorCopyResult queryList() {
        //创建返回到页面的备份对象
        SensorCopyResult copyResult=new SensorCopyResult();
        //从数据库查出最大值
        Integer maxTemp = sensorCopyMapper.maxTemp();
        Integer maxHum = sensorCopyMapper.maxHum();
        Integer maxLight = sensorCopyMapper.maxLight();
        Integer maxAirPress = sensorCopyMapper.maxAirPress();
        //从数据库查询最小值
        Integer minTemp = sensorCopyMapper.minTemp();
        Integer minHum = sensorCopyMapper.minHum();
        Integer minLight = sensorCopyMapper.minLight();
        Integer minAirPress = sensorCopyMapper.minAirPress();
        //从数据库查询平均值
        Integer avgTemp = sensorCopyMapper.avgTemp();
        Integer avgHum = sensorCopyMapper.avgHum();
        Integer avgLight = sensorCopyMapper.avgLight();
        Integer avgAirPress = sensorCopyMapper.avgAirPress();

        //从数据库查询是否有人次数
        Integer notHuman = sensorCopyMapper.notHuman(); //没有人次数
        Integer isHuman = sensorCopyMapper.isHuman(); //有人次数

        //封装
        copyResult.setTempList(Arrays.asList(maxTemp,minTemp,avgTemp));
        copyResult.setHumList(Arrays.asList(maxHum,minHum,avgHum));
        copyResult.setLightList(Arrays.asList(maxLight,minLight,avgLight));
        copyResult.setPressList(Arrays.asList(maxAirPress,minAirPress,avgAirPress));
        copyResult.setHumanList(Arrays.asList(notHuman,isHuman));

        return copyResult;
    }
}
