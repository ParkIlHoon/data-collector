package io.hoon.datacollector.service;

import io.hoon.datacollector.dto.CollectedDataDto;
import io.hoon.datacollector.dto.DataCollectReqDto;
import io.hoon.datacollector.dto.DataCollectRespDto;
import io.hoon.datacollector.repository.InMemoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <h1>데이터 수집 서비스</h1>
 * {@link DataCollectReqDto 수집 요청 받은 데이터}를 가공해 {@link InMemoryRepository 임시 저장소}에 저장하고, {@link CollectedDataDto 수집 데이터}를 가져옵니다.
 */
@Service
@RequiredArgsConstructor
public class DataCollectorService {

    private final InMemoryRepository inMemoryRepository;

    public DataCollectRespDto collectData(DataCollectReqDto dataCollectReqDto) {
        boolean result = inMemoryRepository.save(dataCollectReqDto);
        return new DataCollectRespDto()
            .setProdType(dataCollectReqDto.getProdType())
            .setDataType(dataCollectReqDto.getDataType())
            .setSuccess(result);
    }

    public List<CollectedDataDto> getData() {
        return inMemoryRepository.getData();
    }
}
