package io.hoon.datacollector.service;

import io.hoon.datacollector.common.aop.PublishDataCollectEvent;
import io.hoon.datacollector.common.utils.HttpRequestUtil;
import io.hoon.datacollector.dto.CollectedDataDto;
import io.hoon.datacollector.dto.DataCollectReqDto;
import io.hoon.datacollector.dto.DataCollectRespDto;
import io.hoon.datacollector.repository.InMemoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 요청 받은 데이터를 임시 저장소에 저장합니다.
     *
     * @param dataCollectReqDto 수집 요청 데이터
     * @return 데이터 수집 결과
     * @see PublishDataCollectEvent
     * @see io.hoon.datacollector.common.aop.AopAdvisor
     */
    @PublishDataCollectEvent
    public DataCollectRespDto collectData(DataCollectReqDto dataCollectReqDto) throws InterruptedException {
        CollectedDataDto collectedDataDto = new CollectedDataDto()
            .setProdType(dataCollectReqDto.getProdType())
            .setDataType(dataCollectReqDto.getDataType())
            .setClientIp(HttpRequestUtil.getRemoteIp())
            .setClientLocale(HttpRequestUtil.getLocale().getISO3Country())
            .setData(dataCollectReqDto.getData());

        // 데이터 임시 저장
        inMemoryRepository.save(collectedDataDto);
        return new DataCollectRespDto()
            .setProdType(dataCollectReqDto.getProdType())
            .setDataType(dataCollectReqDto.getDataType())
            .setSuccess(true);
    }

    public List<CollectedDataDto> getData() {
        return inMemoryRepository.getData();
    }
}
