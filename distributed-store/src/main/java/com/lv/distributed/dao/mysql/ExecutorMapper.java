package com.lv.distributed.dao.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lv.distributed.bean.mysql.ExecutorPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExecutorMapper extends BaseMapper<ExecutorPO> {
}
