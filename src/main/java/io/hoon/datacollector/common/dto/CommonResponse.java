package io.hoon.datacollector.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(title = "공통 응답 객체")
public class CommonResponse<T> {

    @Schema(title = "결과 코드", example = "0", description = "0 : 성공, 0 이상 : 실패")
    private int code = 0;

    @Schema(title = "결과 메시지")
    private String message = "SUCCESS";

    @Schema(title = "결과 데이터")
    private T result;

    public static <R> CommonResponse<R> of (R result) {
        CommonResponse<R> ret = new CommonResponse<>();
        ret.setResult(result);
        return ret;
    }
    @SuppressWarnings("squid:S3740")
    public static CommonResponse empty() {
        return new CommonResponse();
    }
}
