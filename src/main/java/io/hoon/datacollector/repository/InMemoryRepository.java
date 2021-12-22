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
        /*
         * FIXME Queue.add() 의 경우 예외를 던지기 때문에, 응답 데이터에 결과(boolean)을 전달하기 위해서는 Queue.offer() 를 사용하는 것이 나아보임.
         *       엘리먼트 추가 실패 시 blocking 하고 대기하는 것이 안전할 것으로 보이기 때문에 Queue.put() 을 사용하는 것이 베스트.
         */
        return this.repository.add(new CollectedDataDto()
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
        while (!this.repository.isEmpty()) {
            rtn.add(this.repository.poll());
        }
        return rtn;
    }
}
