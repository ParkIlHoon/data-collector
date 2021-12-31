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
import javax.annotation.PostConstruct;
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
    private static final String TEMP_PATH = "/temp";
    private final ObjectMapper objectMapper;

    @Value("${writer.file.root-path:/}")
    private String fileRootPath;

    private Path filePath;

    /**
     * 파일 디렉토리를 생성합니다.
     * @throws IOException
     */
    @PostConstruct
    void initialize() throws IOException {
        Files.createDirectories(Path.of(this.fileRootPath));
        Files.createDirectories(Path.of(this.fileRootPath, TEMP_PATH));
    }

    @Override
    public boolean isWritable() {
        return this.filePath != null && Files.exists(this.filePath);
    }

    @Override
    public void write(Collection<CollectedDataDto> dataCollection) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        for (CollectedDataDto collectedData : dataCollection) {
            stringBuffer.append(objectMapper.writeValueAsString(collectedData));
            stringBuffer.append(SEPARATOR);
        }
        String valueAsString = stringBuffer.toString();
        Files.write(this.filePath, valueAsString.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
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
        moveTempFileToStage();
        createTempFile();
    }

    /**
     * 임시 파일이 존재할 경우, data shipper 가 수집하는 디렉토리로 이동시킵니다.
     * @throws IOException
     */
    private void moveTempFileToStage() throws IOException {
        if (this.filePath != null) {
            Files.move(this.filePath, Path.of(this.fileRootPath, this.filePath.getFileName().toString()));
            log.info("파일이 이동되었습니다.");
            this.filePath = null;
        }
    }

    /**
     * 임시 파일을 생성합니다.
     * @throws IOException
     */
    private void createTempFile() throws IOException {
        String fileName = createFileName();
        Path path = Path.of(this.fileRootPath, TEMP_PATH, fileName);
        this.filePath = Files.write(path, "".getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
        log.info("임시 파일이 생성되었습니다.");
    }

    private String createFileName() {
        return ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HHmmss")) + ".txt";
    }
}
