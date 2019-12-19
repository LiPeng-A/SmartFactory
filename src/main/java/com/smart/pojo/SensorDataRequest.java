package com.smart.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class SensorDataRequest {
    private String id;
    private String date;
    private Integer pageNum;
    private Integer pageSize;
}
