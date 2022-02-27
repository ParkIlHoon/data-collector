package io.hoon.datacollector.common.properties;

import io.hoon.datacollector.common.exception.KeyNotFoundException;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@AllArgsConstructor
@ConfigurationProperties(prefix = "keystore")
public class KeyStore {

    private Map<String, String> keys;

    /**
     * 키 존재 여부를 반환합니다.
     *
     * @param key 검사할 키
     * @return 검사할 키가 존재할 경우 {@code true}, 존재하지 않을 경우 {@code false}
     */
    public boolean hasKey(String key) {
        return this.keys.containsValue(key);
    }

    /**
     * 키 이름을 반환합니다.
     *
     * @param key 키
     * @return 키 이름
     * @throws KeyNotFoundException 존재하지 않는 키일 경우
     */
    public String getKeyName(String key) {
        return this.keys.entrySet().stream().filter(e -> e.getValue().equals(key)).findFirst().orElseThrow(KeyNotFoundException::new).getKey();
    }
}
