package com.xj.dynamicthreadpoolspringbootstarter.registry;

import com.xj.dynamicthreadpoolspringbootstarter.domain.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * @author : xiongjun
 * @date : 2024/5/21 15:21
 * @description: 注册中心接口
 */
public interface IRegistry {

    void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolEntities);

    void reportThreadPoolConfigParameter(ThreadPoolConfigEntity threadPoolConfigEntity);

}
