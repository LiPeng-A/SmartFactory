package com.smart.mapper;

import com.smart.pojo.Sensor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SensorMapper extends Mapper<Sensor> {
    @Select("select avg(light) from tb_sensor")
    Integer getLightAvg();
    @Select("select avg(air_pressure) from tb_sensor")
    Integer getPressAvg();
    @Select("select * from (select * from tb_sensor order by id desc LIMIT 40)tb_sensor order by id asc")
    List<Sensor> getSensor();
    @Select("select max(temperature) from tb_sensor")
    Integer getMaxTemp();
    @Select("select max(humidity) from tb_sensor")
    Integer getMaxHum();
    @Select("select max(light) from tb_sensor")
    Integer getMaxLight();
    @Select("select max(air_pressure) from tb_sensor")
    Integer getMaxAirPress();
    @Select("select count(*) from tb_sensor")
    Integer getCount();

    @Delete("delete from tb_sensor")
    void deleteAnyData();

    @Insert("insert into tb_sensor_copy select * from tb_sensor")
    void copyAnyData();


}
