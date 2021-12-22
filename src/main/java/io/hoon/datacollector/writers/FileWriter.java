package io.hoon.datacollector.writers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hoon.datacollector.dto.CollectedDataDto;
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
 * 전달받은 수집 데이터를 {@code .txt} 파일로 저장합니다. 데이터가 작성되는 파일은 1시간 마다 새로 생성됩니다.
 *
 * @see io.hoon.datacollector.writers.Writer
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FileWriter implements Writer{

    private static final String SEPARATOR = "\n";
    private final ObjectMapper objectMapper;

    @Value("${writer.file.root-path:/}")
    private String fileRootPath;

    private Path filePath;

    @Override
    public boolean isWritable() {
        return this.filePath != null && Files.exists(this.filePath);
    }

    @Override
    public void write(Collection<CollectedDataDto> dataCollection) throws IOException {
        //FIXME Collection 보다 개별 Object 를 각각 JSON String 으로 변환하는 것이 Data Shipper 에서 처리하기 편함.
        String valueAsString = objectMapper.writeValueAsString(dataCollection) + SEPARATOR;
        Files.write(this.filePath, valueAsString.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
    }

    /**
     * 1시간 주기로 데이터를 작성할 파일을 생성합니다.
     */
    @Scheduled(fixedDelay = 1000 * 60 * 60L)
//    @Scheduled(fixedDelay = 1000 * 10L)
    public void createFileTask() throws IOException {
        createFile();
    }

    /**
     * 데이터를 작성할 파일을 생성합니다.
     *
     * @throws IOException
     */
    private void createFile() throws IOException {
        String fileName = createFileName();
        Path path = Path.of(this.fileRootPath, fileName);

        //FIXME Files.exists() 로 존재여부를 판단하기 보다, Files.write() 에 StandardOpenOption 을 넘겨서 아래 로직을 처리하도록 하는 것이 나음. "I/O는 최대한 Atomic 하게"
        if (!Files.exists(path)) {
            this.filePath = Files.createFile(path);
            log.info("파일이 생성되었습니다.");
        } else {
            this.filePath = path;
            log.info("이미 파일이 존재해 해당 파일을 사용합니다.");
        }
    }

    private String createFileName() {
        return ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HHmmss")) + ".txt";
    }
}
