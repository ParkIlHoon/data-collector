package io.hoon.datacollector.repository;

import io.hoon.datacollector.dto.CollectedDataDto;
import io.hoon.datacollector.dto.DataCollectReqDto;
import java.util.HashSet;
import java.util.Set;

/*
 * FIXME 아래와 같이 static 변수로 임시 저장소를 구현할 경우 stackoverflow 를 유발할 수 있음.
 *       여러 Thread 에서 같은 저장소를 사용하려는 구현 의도는 싱글톤을 통해 구현가능하며, 싱글톤을 쓴다면 Spring Bean 으로 만드는 것이 더 좋음.
 */
public class InMemoryRepository {

    /*
     * FIXME 현재 구현된대로 Set 을 사용할 경우, get -> remove 2단계를 거쳐야함.
     *       Queue 를 사용한다면 poll 하면서 삭제까지 되므로 Queue 를 사용하는 것을 추천함.
     */
    private static Set<CollectedDataDto> repository = new HashSet<>();

    public static boolean save(DataCollectReqDto dataCollectReqDto) {
        return repository.add(new CollectedDataDto()
                .setProdType(dataCollectReqDto.getProdType())
                .setDataType(dataCollectReqDto.getDataType())
                .setData(dataCollectReqDto.getData()));
    }

    public static Set<CollectedDataDto> getData() {
        //FIXME 임시 저장소에 저장된 데이터가 많을 경우 성능에 악영향을 줄 수 있음.
        return Set.copyOf(repository);
    }

    public static void removeData(Set<CollectedDataDto> deleteData) {
        repository.removeAll(deleteData);
    }
}
