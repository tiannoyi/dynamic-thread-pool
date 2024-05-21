package com.xj.dynamicthreadpoolspringbootstarter.trigger.job;

import com.alibaba.fastjson.JSON;
import com.xj.dynamicthreadpoolspringbootstarter.domain.IDynamicThreadPoolService;
import com.xj.dynamicthreadpoolspringbootstarter.domain.model.entity.ThreadPoolConfigEntity;
import com.xj.dynamicthreadpoolspringbootstarter.registry.IRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * @author : xiongjun
 * @date : 2024/5/21 15:34
 * @description:  线程池数据上报任务
 */
public class ThreadPoolDataReportJob {

    private Logger logger = LoggerFactory.getLogger(ThreadPoolDataReportJob.class);

    private final IDynamicThreadPoolService dynamicThreadPoolService;

    private final IRegistry registry;


    public ThreadPoolDataReportJob(IDynamicThreadPoolService dynamicThreadPoolService, IRegistry registry) {
        this.dynamicThreadPoolService = dynamicThreadPoolService;
        this.registry = registry;
    }

    /**
     * 看这个定时任务是否需要外部定时调用
     */
    @Scheduled(cron = "0/25 * * * * ?")
    public void execReportThreadPoolList(){
        List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.queryThreadPoolList();
        registry.reportThreadPool(threadPoolConfigEntities);
        logger.info("动态线程池,上报线程池信息{}", JSON.toJSONString(threadPoolConfigEntities));

        for (ThreadPoolConfigEntity threadPoolConfigEntity : threadPoolConfigEntities) {
            registry.reportThreadPoolConfigParameter(threadPoolConfigEntity);
            logger.info("动态线程池,上报线程池配置参数{}", JSON.toJSONString(threadPoolConfigEntity));
        }

    }

}
