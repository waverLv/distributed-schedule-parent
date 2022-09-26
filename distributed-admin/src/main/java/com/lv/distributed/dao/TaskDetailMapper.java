package com.lv.distributed.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lv.distributed.bean.TaskDetailPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskDetailMapper extends BaseMapper<TaskDetailPO> {
}
