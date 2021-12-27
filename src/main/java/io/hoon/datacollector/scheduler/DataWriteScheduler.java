package io.hoon.datacollector.scheduler;

import io.hoon.datacollector.manager.DataWriteManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * <h1>데이터 작성 스케쥴러</h1>
 * 1초마다 {@link DataWriteManager 데이터 작성 메니저}의 데이터 작성 메서드를 호출합니다.<br>
 * {@code scheduler.data-write.enabled} 속성이 true 이어야 동작합니다.
 * @see DataWriteManager
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "scheduler.data-write.enabled", matchIfMissing = false, havingValue = "true")
public class DataWriteScheduler {
    private final DataWriteManager dataWriteManager;

    @Scheduled(fixedDelay = 1000L)
    public void writeDataTask() throws Exception {
        log.info("스케쥴 테스크를 실행합니다.");
        dataWriteManager.writeDataTask();
    }
}
