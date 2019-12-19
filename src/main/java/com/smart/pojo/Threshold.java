package com.smart.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_threshold")
public class Threshold {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String time;
    private Integer temp_threshold; //温度阈值
    private Integer hum_threshold; //湿度阈值
    private Integer light_threshold; //光亮阈值
    private String remark; //描述
}
