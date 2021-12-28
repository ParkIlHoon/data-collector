package io.hoon.datacollector.service;

import io.hoon.datacollector.common.event.DataCollectEvent;
import io.hoon.datacollector.dto.CollectedDataDto;
import io.hoon.datacollector.dto.DataCollectReqDto;
import io.hoon.datacollector.dto.DataCollectRespDto;
import io.hoon.datacollector.repository.InMemoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * <h1>데이터 수집 서비스</h1>
 * {@link DataCollectReqDto 수집 요청 받은 데이터}를 가공해 {@link InMemoryRepository 임시 저장소}에 저장하고, {@link CollectedDataDto 수집 데이터}를 가져옵니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataCollectorService {

    private final InMemoryRepository inMemoryRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * 요청 받은 데이터를 임시 저장소에 저장하고 {@link DataCollectEvent 데이터 수집 이벤트}를 발행합니다.
     *
     * @param dataCollectReqDto 수집 요청 데이터
     * @return 데이터 수집 결과
     * @see io.hoon.datacollector.manager.DataWriteManager
     */
    public DataCollectRespDto collectData(DataCollectReqDto dataCollectReqDto) {
        // 데이터 임시 저장
        boolean result = inMemoryRepository.save(dataCollectReqDto);

        // 이벤트 발행
        //FIXME 일부러 심플하게 한 것이라면 패스, AOP 로 이벤트를 발행해도 괜찮을 것 같음
        if (result) {
            applicationEventPublisher.publishEvent(new DataCollectEvent());
            log.info("이벤트를 발행했습니다.");
        }

        return new DataCollectRespDto()
            .setProdType(dataCollectReqDto.getProdType())
            .setDataType(dataCollectReqDto.getDataType())
            .setSuccess(result);
    }

    public List<CollectedDataDto> getData() {
        return inMemoryRepository.getData();
    }
}
