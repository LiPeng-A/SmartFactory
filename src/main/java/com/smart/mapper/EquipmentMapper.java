package com.smart.mapper;

import com.smart.pojo.Equipment;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface EquipmentMapper extends Mapper<Equipment> {

    @Update("update tb_equipment set status=0")
    void updateStatus();
}
