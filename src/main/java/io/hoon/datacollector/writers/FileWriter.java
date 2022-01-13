package io.hoon.datacollector.writers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hoon.datacollector.domain.CollectedData;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * <h1>로컬 파일 저장 클래스</h1>
 * 전달받은 수집 데이터를 {@value #FILE_EXT} 파일로 저장합니다. 데이터가 작성되는 파일은 1시간 마다 새로 생성됩니다.
 *
 * @see io.hoon.datacollector.writers.Writer
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileWriter implements Writer{

    private static final String SEPARATOR = "\n";
    private static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd-HHmmss");
    private static final String FILE_EXT = ".txt";
    private final ObjectMapper objectMapper;

    @Value("${writer.file.root-path:/}")
    private String fileRootPath;

    private Path filePath;

    @Override
    public boolean isWritable() {
        return this.filePath != null && Files.exists(this.filePath);
    }

    @Override
    public void write(Collection<CollectedData> dataCollection) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (CollectedData collectedData : dataCollection) {
            stringBuilder.append(objectMapper.writeValueAsString(collectedData));
            stringBuilder.append(SEPARATOR);
        }
        String valueAsString = stringBuilder.toString();
        Files.write(this.filePath, valueAsString.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    /**
     * 1시간 주기로 데이터를 작성할 파일을 생성합니다.
     */
//    @Scheduled(fixedDelay = 1000 * 60 * 60L)
    @Scheduled(fixedDelay = 1000 * 10L)
    public void createFileTask() throws IOException {
        createFile();
    }

    /**
     * 데이터를 작성할 파일을 생성합니다.
     *
     * @throws IOException
     */
    private void createFile() throws IOException {
        Path path = Path.of(this.fileRootPath, createFileName());
        this.filePath = Files.createFile(path);
        log.info("파일이 생성되었습니다.");
    }

    private String createFileName() {
        return ZonedDateTime.now().format(FILE_NAME_FORMATTER) + FILE_EXT;
    }
}
