package com.smart.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_sensor_copy")
public class SensorCopy {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private Integer temperature; //温度
    private Integer humidity; //湿度
    private Integer light; //光照强度
    private Integer air_pressure; //气压
    private String now_time; //当前采集时间
    private Integer is_human; //判断是否有人  #1有 0没有
}
