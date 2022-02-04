package io.hoon.datacollector.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(title = "데이터 수집 요청 DTO")
public class DataCollectReqDto {
    @Schema(title = "제품 타입", required = true)
    @NotBlank(message = "제품 타입은 필수값입니다.")
    private String prodType;

    @Schema(title = "데이터 타입", required = true)
    @NotBlank(message = "데이터 타입은 필수값입니다.")
    private String dataType;

    @Schema(title = "수집 데이터",
        required = true,
        example = "{\"key\" : \"value\", \"arr\" : [1, 2, 3], \"subObj\" : {\"sub-key\":\"sub-value\"}}")
    @NotBlank(message = "데이터는 필수값입니다.")
    private String data;
}
