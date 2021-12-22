package io.hoon.datacollector.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hoon.datacollector.dto.CollectedDataDto;
import io.hoon.datacollector.repository.InMemoryRepository;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.concurrent.Future;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileWriteScheduler {

    private final ObjectMapper objectMapper;

    //FIXME property 로 개선하는 것이 좋을 것 같음.
    private static final String FILE_PATH = "/Users/nhn/Documents";

    @Scheduled(cron = "0 0 * * * *")
//    @Scheduled(fixedDelay = 10000L)
    public void fileWriteTask() {
        Set<CollectedDataDto> sourceData = InMemoryRepository.getData();
        String fileName = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".txt";
        Path path = Path.of(FILE_PATH, fileName);

        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                //FIXME e.printStackTrace 는 아무래도...
                e.printStackTrace();
            }
        }

        //FIXME 아래 AsynchronousFileChannel 을 사용하는 코드는 java.nio.file.Files 의 write 메서드에 구현되어있어 할 필요가 없다.
        try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            String collectData = objectMapper.writeValueAsString(sourceData);

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(collectData.getBytes(StandardCharsets.UTF_8));

            Future<Integer> operation = fileChannel.write(byteBuffer, 0);
            byteBuffer.clear();

            while (!operation.isDone());

            log.info("파일 생성 완료! " + path.toString());
            InMemoryRepository.removeData(sourceData);
            log.info("메모리 데이터 삭제 완료!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
