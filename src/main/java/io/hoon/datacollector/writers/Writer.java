package io.hoon.datacollector.writers;

import io.hoon.datacollector.domain.CollectedData;
import java.util.Collection;

/**
 * 수집된 데이터를 저장하는 클래스를 추상화한 인터페이스
 * <ul>
 *  <li>{@link io.hoon.datacollector.writers.FileWriter} 데이터를 로컬 파일로 저장하는 구현체</li>
 *  <li>{@link io.hoon.datacollector.writers.DBEachRowWriter} 데이터를 DB에 각각의 Row 로 저장하는 구현체</li>
 * </ul>
 */
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
    @SuppressWarnings("squid:S112")
    void write(Collection<CollectedData> dataCollection) throws Exception;
}
