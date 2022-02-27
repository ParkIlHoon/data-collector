package io.hoon.datacollector.domain;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CollectedData {
    private String prodType;
    private String dataType;
    private String data;
    private ZonedDateTime collectedDateTime = ZonedDateTime.now();
    private String clientIp;
    private String clientLocale;
    private String secretKey;
}
