package io.penguinstats.penguinbotx.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ：yamika
 * @date ：Created in 2020/5/25 17:42
 * @description ：
 * @modified By： yamika
 * @version: 0.1
 */

@Slf4j
@EnableAsync
@EnableScheduling
@Configuration
public class ExecutorConfig implements SchedulingConfigurer {

    @Bean(name = "asyncExecutor")
    public ThreadPoolTaskExecutor asyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(64);
        executor.setQueueCapacity(10);
        executor.setAllowCoreThreadTimeOut(true);
        executor.setKeepAliveSeconds(120);
        executor.setThreadNamePrefix("asyncExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;

    }

    /**
     * 定时任务使用的线程池
     * @return
     */
    @Bean(destroyMethod = "shutdown", name = "taskScheduler")
    public ThreadPoolTaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("task-");
        scheduler.setAwaitTerminationSeconds(600);
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = taskScheduler();
        scheduledTaskRegistrar.setTaskScheduler(taskScheduler);
    }
}
