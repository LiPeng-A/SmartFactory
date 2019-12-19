package com.smart.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "tb_contextual_model")
public class ContextualModel {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    private Integer status;
}
