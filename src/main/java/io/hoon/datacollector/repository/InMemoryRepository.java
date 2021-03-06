package io.hoon.datacollector.repository;

import io.hoon.datacollector.domain.CollectedData;
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

    private Queue<CollectedData> repository = new LinkedBlockingQueue<>();

    public void save(CollectedData collectedData) throws InterruptedException {
        ((LinkedBlockingQueue)this.repository).put(collectedData);
    }

    /**
     * 임시 저장된 모든 데이터를 조회합니다.
     * @return 임시 저장된 모든 {@link CollectedData 수집 데이터}
     */
    public List<CollectedData> getData() {
        List<CollectedData> rtn = new ArrayList<>();
        ((LinkedBlockingQueue) this.repository).drainTo(rtn);
        return rtn;
    }
}
