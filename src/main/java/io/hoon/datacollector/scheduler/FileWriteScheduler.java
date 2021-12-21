package io.hoon.datacollector.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hoon.datacollector.repository.InMemoryRepository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileWriteScheduler {

    private final ObjectMapper objectMapper;
    private final InMemoryRepository inMemoryRepository;

    @Value("${writer.file.path:/}")
    private String filePath;

//    @Scheduled(cron = "0 0 * * * *")
    @Scheduled(fixedDelay = 10000L)
    public void fileWriteTask() throws IOException {
        String fileName = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".txt";
        Path path = Path.of(filePath, fileName);

        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        String collectData = objectMapper.writeValueAsString(inMemoryRepository.getData());
        Files.write(path, collectData.getBytes(StandardCharsets.UTF_8));
    }

}
