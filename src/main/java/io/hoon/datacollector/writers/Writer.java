package io.hoon.datacollector.writers;

import io.hoon.datacollector.dto.CollectedDataDto;
import java.util.Collection;

public interface Writer {

    /**
     * 현재 데이터 작성이 가능한지 여부를 반환합니다.
     *
     * @return 데이터 작성 가능 여부
     */
    boolean isWritable();

    /**
     * 데이터를 작성합니다.
     *
     * @param dataCollection 작성할 데이터 컬렉션
     * @throws Exception
     */
    void write(Collection<CollectedDataDto> dataCollection) throws Exception;
}
