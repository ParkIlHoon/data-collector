package io.hoon.datacollector.repository;

import io.hoon.datacollector.dto.CollectedDataDto;
import io.hoon.datacollector.dto.DataCollectReqDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.stereotype.Component;

/**
 * <h1>메모리 임시 저장소</h1>
 * 수집 요청 받은 데이터를 메모리에 임시로 저장하는 클래스
 */
@Component
public class InMemoryRepository {

    private Queue<CollectedDataDto> repository = new LinkedBlockingQueue<>();

    public boolean save(DataCollectReqDto dataCollectReqDto) {
        //FIXME 결과를 리턴해 클라이언트에서의 처리를 늘리는 것 보다 put 으로 큐에 저장될 때 까지 대기하는 것이 간단하고 나을 것 같음
        return this.repository.offer(new CollectedDataDto()
                .setProdType(dataCollectReqDto.getProdType())
                .setDataType(dataCollectReqDto.getDataType())
                .setData(dataCollectReqDto.getData()));
    }

    /**
     * 임시 저장된 모든 데이터를 조회합니다.
     * @return 임시 저장된 모든 {@link CollectedDataDto 수집 데이터}
     */
    public List<CollectedDataDto> getData() {
        List<CollectedDataDto> rtn = new ArrayList<>();
        ((LinkedBlockingQueue) this.repository).drainTo(rtn);
        return rtn;
    }
}
