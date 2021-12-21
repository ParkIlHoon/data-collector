package io.hoon.datacollector.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(title = "데이터 수집 요청 DTO")
public class DataCollectReqDto {
    @Schema(title = "제품 타입", required = true)
    private String prodType;
    @Schema(title = "데이터 타입", required = true)
    private String dataType;
    @Schema(title = "수집 데이터", required = true)
    private String data;
}
