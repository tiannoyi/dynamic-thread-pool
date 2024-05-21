package com.xj.dynamicthreadpoolspringbootstarter.domain.model;

import com.alibaba.fastjson.JSON;
import com.xj.dynamicthreadpoolspringbootstarter.domain.IDynamicThreadPoolService;
import com.xj.dynamicthreadpoolspringbootstarter.domain.model.entity.ThreadPoolConfigEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author : xiongjun
 * @date : 2024/5/21 14:43
 */
public class DynamicThreadPoolService implements IDynamicThreadPoolService {

    private final Logger logger = LoggerFactory.getLogger(DynamicThreadPoolService.class);

    private final Map<String, ThreadPoolExecutor> threadPoolExecutorMap;

    private final String applicationName;

    public DynamicThreadPoolService(String applicationName, Map<String, ThreadPoolExecutor> threadPoolExecutorMap) {
        this.applicationName = applicationName;
        this.threadPoolExecutorMap = threadPoolExecutorMap;
    }

    @Override
    public List<ThreadPoolConfigEntity> queryThreadPoolList() {
        Set<String> threadPoolBeanNames = threadPoolExecutorMap.keySet();
        List<ThreadPoolConfigEntity> threadPoolVOS = new ArrayList<>(threadPoolBeanNames.size());
        for (String threadPoolBeanName : threadPoolBeanNames) {
            ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(threadPoolBeanName);
            ThreadPoolConfigEntity threadPoolConfigEntityVO = new ThreadPoolConfigEntity(applicationName, threadPoolBeanName);
            threadPoolConfigEntityVO.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
            threadPoolConfigEntityVO.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize());
            threadPoolConfigEntityVO.setActiveCount(threadPoolExecutor.getActiveCount());
            threadPoolConfigEntityVO.setPoolSize(threadPoolExecutor.getPoolSize());
            threadPoolConfigEntityVO.setQueueType(threadPoolExecutor.getQueue().getClass().getName());
            threadPoolConfigEntityVO.setQueueSize(threadPoolExecutor.getQueue().size());
            threadPoolConfigEntityVO.setRemainingCapacity(threadPoolExecutor.getQueue().remainingCapacity());
            logger.info("线程池配置信息：{}", threadPoolConfigEntityVO);
            threadPoolVOS.add(threadPoolConfigEntityVO);
        }
        return threadPoolVOS;
    }

    @Override
    public ThreadPoolConfigEntity queryThreadPoolConfigByName(String threadPoolName) {
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(threadPoolName);
        ThreadPoolConfigEntity threadPoolConfigEntity = new ThreadPoolConfigEntity(applicationName, threadPoolName);
        if (null == threadPoolExecutor) {
            logger.warn("线程池{}不存在", threadPoolName);
            return threadPoolConfigEntity;
        }
        //线程池配置数据
        threadPoolConfigEntity.setCorePoolSize(threadPoolExecutor.getCorePoolSize());
        threadPoolConfigEntity.setMaximumPoolSize(threadPoolExecutor.getMaximumPoolSize());
        threadPoolConfigEntity.setActiveCount(threadPoolExecutor.getActiveCount());
        threadPoolConfigEntity.setPoolSize(threadPoolExecutor.getPoolSize());
        threadPoolConfigEntity.setQueueType(threadPoolExecutor.getQueue().getClass().getName());
        threadPoolConfigEntity.setQueueSize(threadPoolExecutor.getQueue().size());
        threadPoolConfigEntity.setRemainingCapacity(threadPoolExecutor.getQueue().remainingCapacity());

        if (logger.isDebugEnabled()){
            logger.info("动态线程池,配置查询 应用名:{},线程名:{},池化配置:{}",applicationName,threadPoolName, JSON.toJSONString(threadPoolConfigEntity));
        }
        return threadPoolConfigEntity;
    }

    @Override
    public void updateThreadPoolConfig(ThreadPoolConfigEntity threadPoolConfigEntity) {
        if (null == threadPoolConfigEntity || !applicationName.equals(threadPoolConfigEntity.getAppName())) {
            logger.warn("线程池配置信息为空");
            return;
        }
        ThreadPoolExecutor threadPoolExecutor = threadPoolExecutorMap.get(threadPoolConfigEntity.getThreadPoolName());
        if (null == threadPoolExecutor) {
            logger.warn("线程池{}不存在", threadPoolConfigEntity.getThreadPoolName());
            return;
        }
        //设置参数
        threadPoolExecutor.setCorePoolSize(threadPoolConfigEntity.getCorePoolSize());
        threadPoolExecutor.setMaximumPoolSize(threadPoolConfigEntity.getMaximumPoolSize());
    }
}
