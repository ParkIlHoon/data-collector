package io.hoon.datacollector.dto;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CollectedDataDto {
    private String prodType;
    private String dataType;
    private String data;
    private ZonedDateTime collectedDateTime = ZonedDateTime.now();
}
