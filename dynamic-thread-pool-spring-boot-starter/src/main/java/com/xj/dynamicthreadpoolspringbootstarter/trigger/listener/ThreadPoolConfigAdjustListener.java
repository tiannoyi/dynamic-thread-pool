package com.xj.dynamicthreadpoolspringbootstarter.trigger.listener;

import com.alibaba.fastjson2.JSON;
import com.xj.dynamicthreadpoolspringbootstarter.domain.IDynamicThreadPoolService;
import com.xj.dynamicthreadpoolspringbootstarter.domain.model.entity.ThreadPoolConfigEntity;
import com.xj.dynamicthreadpoolspringbootstarter.registry.IRegistry;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author : xiongjun
 * @date : 2024/5/23 16:06
 * @description : 动态线程池变更监听器
 */
public class ThreadPoolConfigAdjustListener implements MessageListener<ThreadPoolConfigEntity> {

    private Logger logger = LoggerFactory.getLogger(ThreadPoolConfigAdjustListener.class);

    private final IDynamicThreadPoolService dynamicThreadPoolService;

    private final IRegistry registry;

    public ThreadPoolConfigAdjustListener(IDynamicThreadPoolService dynamicThreadPoolService, IRegistry registry) {
        this.dynamicThreadPoolService = dynamicThreadPoolService;
        this.registry = registry;
    }

    @Override
    public void onMessage(CharSequence channel, ThreadPoolConfigEntity entity) {
        logger.info("动态线程池，调整线程池配置。线程池名称:{} 核心线程数:{} 最大线程数:{}",
                entity.getThreadPoolName(), entity.getCorePoolSize(), entity.getMaximumPoolSize());
        dynamicThreadPoolService.updateThreadPoolConfig(entity);

        List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.queryThreadPoolList();
        registry.reportThreadPool(threadPoolConfigEntities);

        ThreadPoolConfigEntity threadPoolConfigEntity = dynamicThreadPoolService.queryThreadPoolConfigByName(entity.getThreadPoolName());
        registry.reportThreadPoolConfigParameter(threadPoolConfigEntity);
        logger.info("线程池配置信息：{}", JSON.toJSONString(threadPoolConfigEntity));

    }


}
