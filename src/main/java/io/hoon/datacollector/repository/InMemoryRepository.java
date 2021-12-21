package io.hoon.datacollector.repository;

import io.hoon.datacollector.dto.CollectedDataDto;
import io.hoon.datacollector.dto.DataCollectReqDto;
import java.util.HashSet;
import java.util.Set;

public class InMemoryRepository {

    private static Set<CollectedDataDto> repository = new HashSet<>();

    public static boolean save(DataCollectReqDto dataCollectReqDto) {
        return repository.add(new CollectedDataDto()
                .setProdType(dataCollectReqDto.getProdType())
                .setDataType(dataCollectReqDto.getDataType())
                .setData(dataCollectReqDto.getData()));
    }

    public static Set<CollectedDataDto> getData() {
        return Set.copyOf(repository);
    }

    public static void removeData(Set<CollectedDataDto> deleteData) {
        repository.removeAll(deleteData);
    }
}
