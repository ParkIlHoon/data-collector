package io.hoon.datacollector.common.event;

import io.hoon.datacollector.manager.DataWriteManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
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

    @EventListener
    public void dataCollectEventListener(DataCollectEvent dataCollectEvent) throws Exception {
        dataWriteManager.writeDataTask();
    }
}
