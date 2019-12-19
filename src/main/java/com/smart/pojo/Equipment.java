package com.smart.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "tb_equipment")
@NoArgsConstructor
public class Equipment {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id; //id
    private String equ_name; //设备名称
    private String model; //设备型号
    private String end_time; //最后访问时间
    private Integer status; //设备状态
    private Integer relay_no; // 继电器接口

    public Equipment(Integer relay_no){
        this.relay_no=relay_no;
    }
}
