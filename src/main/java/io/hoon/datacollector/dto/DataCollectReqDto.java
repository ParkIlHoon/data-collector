package io.hoon.datacollector.dto;

import io.hoon.datacollector.common.validation.annotation.DataJsonTemplate;
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
        description = "수집 요청 데이터는 데이터를 대표하는 하나의 키를 가지는 JSON object 하위에 존재해야합니다. (Example Value 참고)",
        example = "{\"dataTypeName\" : {\"key\" : \"value\", \"arr\" : [1, 2, 3], \"subObj\" : {\"sub-key\":\"sub-value\"}}}")
    @NotBlank(message = "데이터는 필수값입니다.")
    @DataJsonTemplate
    private String data;
}
