package io.hoon.datacollector.manager;

import io.hoon.datacollector.dto.CollectedDataDto;
import io.hoon.datacollector.service.DataCollectorService;
import io.hoon.datacollector.writers.Writer;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * <h1>데이터 작성 매니저</h1>
 * {@link io.hoon.datacollector.repository.InMemoryRepository 임시 저장소}에 저장된 {@link CollectedDataDto 수집 데이터}를 구현된 {@link Writer} 클래스를 통해 작성합니다.<br>
 * 아래의 경우 데이터를 작성하지 않고 종료됩니다.
 * <ul>
 *     <li>구현된 {@link Writer} Bean 이 존재하지 않는 경우</li>
 *     <li>모든 {@link Writer} Bean 이 작성 가능한 상태가 아닌 경우</li>
 *     <li>작성할 {@link CollectedDataDto 수집 데이터}가 존재하지 않는 경우 </li>
 * </ul>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataWriteManager {

    private final DataCollectorService dataCollectorService;
    private final List<Writer> writers;

    @Async
    public void writeDataTask() throws Exception {
        Optional<Writer> anyWritableWriter = writers.stream().filter(Writer::isWritable).findAny();
        if (anyWritableWriter.isEmpty()) {
            log.info("현재 작성 가능한 Writer 가 존재하지 않아 건너뜁니다.");
            return;
        }

        List<CollectedDataDto> collectedData = dataCollectorService.getData();
        if (collectedData.isEmpty()) {
            log.debug("작성할 데이터가 존재하지 않아 건너뜁니다.");
            return;
        }

        for (Writer writer : writers) {
            if (!writer.isWritable()) {
                continue;
            }

            writer.write(collectedData);
        }
        log.info("데이터를 작성했습니다.");
    }
}
