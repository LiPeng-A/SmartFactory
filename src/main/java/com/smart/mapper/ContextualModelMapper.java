package com.smart.mapper;

import com.smart.pojo.ContextualModel;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface ContextualModelMapper extends Mapper<ContextualModel> {

    @Update("update tb_contextual_model set status=0")
    void closeModel();
}
