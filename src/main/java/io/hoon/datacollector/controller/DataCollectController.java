package io.hoon.datacollector.controller;

import io.hoon.datacollector.common.dto.CommonResponse;
import io.hoon.datacollector.dto.DataCollectReqDto;
import io.hoon.datacollector.dto.DataCollectRespDto;
import io.hoon.datacollector.service.DataCollectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "데이터 수집")
@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataCollectController {

    private final DataCollectorService dataCollectorService;

    @PostMapping
    @Operation(summary = "데이터 수집 요청")
    public CommonResponse<DataCollectRespDto> collectRequest(@Parameter(name = "데이터 수집 요청 DTO", required = true)
                                                             @RequestBody
                                                             @Valid DataCollectReqDto dataCollectReqDto) {
        DataCollectRespDto respDto = null;
        try {
            respDto = dataCollectorService.collectData(dataCollectReqDto);
        } catch (InterruptedException e) {
            respDto = new DataCollectRespDto().setDataType(dataCollectReqDto.getDataType()).setProdType(dataCollectReqDto.getProdType()).setSuccess(false);
        } finally {
            return CommonResponse.of(respDto);
        }
    }
}
