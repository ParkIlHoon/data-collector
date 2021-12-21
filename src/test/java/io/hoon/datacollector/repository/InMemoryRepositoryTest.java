package io.hoon.datacollector.repository;

import static org.junit.jupiter.api.Assertions.*;

import io.hoon.datacollector.dto.DataCollectReqDto;
import org.junit.jupiter.api.Test;

class InMemoryRepositoryTest {

    @Test
    void 저장() {
        // given
        int loopCount = 100;

        // when
        for (int idx = 0; idx < loopCount; idx++) {
            InMemoryRepository.save(new DataCollectReqDto().setDataType("data_type").setProdType("prod_type").setData(String.valueOf(idx)));
        }

        // then
        assertEquals(loopCount, InMemoryRepository.getData().size());
    }

    @Test
    void 삭제() {
        // given
        int loopCount = 100;
        for (int idx = 0; idx < loopCount; idx++) {
            InMemoryRepository.save(new DataCollectReqDto().setDataType("data_type").setProdType("prod_type").setData(String.valueOf(idx)));
        }

        // when
        InMemoryRepository.removeData(InMemoryRepository.getData());

        // then
        assertTrue(InMemoryRepository.getData().isEmpty());
    }
}