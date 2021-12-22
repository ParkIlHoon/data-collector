package io.hoon.datacollector.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * <h1>스케쥴러 Thread 설정 클래스</h1>
 * {@link io.hoon.datacollector.writers.FileWriter} 의 파일 생성 스케쥴과 {@link io.hoon.datacollector.scheduler.DataWriteScheduler} 의 데이터 작성 스케쥴이 동일 Thread 에서 동작할 경우,
 * 데이터 작성 테스크가 시간이 오래 소요될 경우 파일 생성 테스크가 지체될 수 있다고 생각해 Spring Scheduler 의 Thread Pool Size 를 조정(기본값 1)
 */
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

    private static final int POOL_SIZE = 5;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(POOL_SIZE);
        threadPoolTaskScheduler.setThreadNamePrefix("schedule-thread-pool-");
        threadPoolTaskScheduler.initialize();

        taskRegistrar.setTaskScheduler(threadPoolTaskScheduler);
    }
}
