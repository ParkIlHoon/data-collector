package io.hoon.datacollector.writers;

import io.hoon.datacollector.domain.CollectedData;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * <h1>DB 저장 클래스</h1>
 * 전달받은 수집 데이터를 각각의 Row 로 DataBase 에 저장합니다.<br>
 * {@link io.hoon.datacollector.scheduler.DataWriteScheduler} 에서 {@code private final List<Writer> writers} 변수에 구현된 {@link Writer} 클래스가 모두 DI 되는지 확인하기 위해 생성된 클래스입니다.
 *
 * @see io.hoon.datacollector.writers.Writer
 */
@Component
@RequiredArgsConstructor
public class DBEachRowWriter implements Writer{

    @Override
    public boolean isWritable() {
        // 스펙에 따라 구현
        return false;
    }

    @Override
    public void write(Collection<CollectedData> dataCollection) throws Exception {
        // 스펙에 따라 구현
    }
}
