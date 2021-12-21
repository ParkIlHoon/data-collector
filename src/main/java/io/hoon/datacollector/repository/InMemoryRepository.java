package io.hoon.datacollector.repository;

import io.hoon.datacollector.dto.CollectedDataDto;
import io.hoon.datacollector.dto.DataCollectReqDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Component;

@Component
public class InMemoryRepository {

    private Queue<CollectedDataDto> repository = new LinkedBlockingQueue<>();

    public boolean save(DataCollectReqDto dataCollectReqDto) {
        return this.repository.add(new CollectedDataDto()
                .setProdType(dataCollectReqDto.getProdType())
                .setDataType(dataCollectReqDto.getDataType())
                .setData(dataCollectReqDto.getData()));
    }

    public List<CollectedDataDto> getData() {
        List<CollectedDataDto> rtn = new ArrayList<>();
        while (!this.repository.isEmpty()) {
            rtn.add(this.repository.poll());
        }
        return rtn;
    }
}
