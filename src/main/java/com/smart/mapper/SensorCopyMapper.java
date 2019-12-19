package com.smart.mapper;

import com.smart.pojo.SensorCopy;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;


public interface SensorCopyMapper extends Mapper<SensorCopy> {

    @Delete("delete from tb_sensor_copy")
    void deleteCopy();

    @Select("select max(temperature) from tb_sensor_copy")
    Integer maxTemp();

    @Select("select max(humidity) from tb_sensor_copy")
    Integer maxHum();

    @Select("select max(light) from tb_sensor_copy")
    Integer maxLight();

    @Select("select max(air_pressure) from tb_sensor_copy")
    Integer maxAirPress();

    @Select("select min(temperature) from tb_sensor_copy")
    Integer minTemp();

    @Select("select min(humidity) from tb_sensor_copy")
    Integer minHum();

    @Select("select min(light) from tb_sensor_copy")
    Integer minLight();

    @Select("select min(air_pressure) from tb_sensor_copy")
    Integer minAirPress();

    @Select("select avg(temperature) from tb_sensor_copy")
    Integer avgTemp();

    @Select("select avg(humidity) from tb_sensor_copy")
    Integer avgHum();

    @Select("select avg(light) from tb_sensor_copy")
    Integer avgLight();

    @Select("select avg(air_pressure) from tb_sensor_copy")
    Integer avgAirPress();

    @Select("select count(is_human) from tb_sensor_copy where is_human=0")
    Integer notHuman();
    @Select("select count(is_human) from tb_sensor_copy where is_human=1")
    Integer isHuman();

}
