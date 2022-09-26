package com.lv.distributed.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("executor_detail")
public class TaskExecutorPO {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String applicationName;
    private Boolean applyStatus;
    private String executorHost;
    private Integer executorPort;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Boolean getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Boolean applyStatus) {
        this.applyStatus = applyStatus;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getExecutorHost() {
        return executorHost;
    }

    public void setExecutorHost(String executorHost) {
        this.executorHost = executorHost;
    }

    public Integer getExecutorPort() {
        return executorPort;
    }

    public void setExecutorPost(Integer executorPort) {
        this.executorPort = executorPort;
    }
}
