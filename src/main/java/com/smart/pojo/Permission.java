package com.smart.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@Table(name = "tb_permission")
public class Permission {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String name;
    private String url;

    @Transient
    private List<Permission> permissionList;
}
