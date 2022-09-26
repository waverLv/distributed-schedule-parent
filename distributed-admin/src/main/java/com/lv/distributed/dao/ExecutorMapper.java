package com.lv.distributed.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lv.distributed.bean.TaskExecutorPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExecutorMapper extends BaseMapper<TaskExecutorPO> {
}
