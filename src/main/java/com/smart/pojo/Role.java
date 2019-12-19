package com.smart.pojo;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;
import sun.nio.cs.US_ASCII;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Data
@Table(name="tb_role")
public class Role {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    private String role_name;
    private String role_desc;
    @Transient
    private List<Permission> permissions;
}
