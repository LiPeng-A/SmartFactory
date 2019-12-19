package com.smart.view;

import com.smart.pojo.SensorCopy;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResult<T> {
    public PageResult(){

    }
    private Long total;
    private List<T> list;
    private Long totalPage;
}
