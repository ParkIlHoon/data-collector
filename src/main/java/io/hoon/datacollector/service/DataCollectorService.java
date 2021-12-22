package io.hoon.datacollector.service;

import io.hoon.datacollector.dto.CollectedDataDto;
import io.hoon.datacollector.dto.DataCollectReqDto;
import io.hoon.datacollector.dto.DataCollectRespDto;
import io.hoon.datacollector.repository.InMemoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
