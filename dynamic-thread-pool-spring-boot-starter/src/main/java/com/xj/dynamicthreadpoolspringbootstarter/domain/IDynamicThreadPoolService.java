package com.xj.dynamicthreadpoolspringbootstarter.domain;

import com.xj.dynamicthreadpoolspringbootstarter.domain.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * @author : xiongjun
 * @date : 2024/5/21 14:35
 * @description: 动态线程池服务
 */
public interface IDynamicThreadPoolService {

    List<ThreadPoolConfigEntity> queryThreadPoolList();

    ThreadPoolConfigEntity queryThreadPoolConfigByName(String threadPoolName);

    void updateThreadPoolConfig(ThreadPoolConfigEntity threadPoolConfigEntity);

}
