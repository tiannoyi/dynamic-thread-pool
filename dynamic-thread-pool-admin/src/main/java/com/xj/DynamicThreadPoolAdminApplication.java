package com.xj;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : xiongjun
 * @date : 2024/5/23 19:22
 */
@SpringBootApplication
@Configurable
class DynamicThreadPoolAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicThreadPoolAdminApplication.class,args);
    }


}