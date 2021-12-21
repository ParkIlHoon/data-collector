package io.hoon.datacollector.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Schema(title = "데이터 수집 응답 DTO")
public class DataCollectRespDto {
    @Schema(title = "제품 타입")
    @NotBlank
    private String prodType;

    @Schema(title = "데이터 타입")
    @NotBlank
    private String dataType;

    @Schema(title = "성공 여부")
    private boolean isSuccess = false;
}
