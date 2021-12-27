package io.hoon.datacollector.common.event;

import io.hoon.datacollector.manager.DataWriteManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <h1>데이터 수집 이벤트 핸들러</h1>
 * {@link DataCollectEvent 데이터 수집 이벤트}가 발생했을때, {@link DataWriteManager 데이터 작성 매니저}의 데이터 작성 메서드를 호출합니다.
 *
 * @see DataCollectEvent
 * @see DataWriteManager
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataCollectEventHandler {

    private final DataWriteManager dataWriteManager;

    /**
     * 데이터 수집 이벤트를 핸들링합니다.
     * Spring 의 ApplicationEventListener 가 비동기가 아니므로, @Async 애너테이션으로 비동기 처리를 해주도록 합니다.
     * @param dataCollectEvent 데이터 수집 이벤트
     * @throws Exception
     */
    @Async
    @EventListener
    public void dataCollectEventListener(DataCollectEvent dataCollectEvent) throws Exception {
        log.info("이벤트를 감지했습니다.");
        dataWriteManager.writeDataTask();
    }
}
