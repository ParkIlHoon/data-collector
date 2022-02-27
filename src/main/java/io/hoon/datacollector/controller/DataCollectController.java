package io.hoon.datacollector.controller;

import io.hoon.datacollector.common.dto.CommonResponse;
import io.hoon.datacollector.common.utils.HttpRequestUtil;
import io.hoon.datacollector.domain.CollectedData;
import io.hoon.datacollector.dto.DataCollectReqDto;
import io.hoon.datacollector.dto.DataCollectRespDto;
import io.hoon.datacollector.service.DataCollectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "데이터 수집")
@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataCollectController {

    private final DataCollectorService dataCollectorService;

    @PostMapping
    @Operation(summary = "데이터 수집 요청")
    @SuppressWarnings("squid:S2142")
    public CommonResponse<DataCollectRespDto> collectRequest(@Parameter(name = "데이터 수집 요청 DTO", required = true)
                                                             @RequestBody
                                                             @Valid DataCollectReqDto dataCollectReqDto) {
        DataCollectRespDto respDto = new DataCollectRespDto().setDataType(dataCollectReqDto.getDataType()).setProdType(dataCollectReqDto.getProdType()).setSuccess(false);
        CollectedData collectedData = new CollectedData()
            .setProdType(dataCollectReqDto.getProdType())
            .setDataType(dataCollectReqDto.getDataType())
            .setClientIp(HttpRequestUtil.getRemoteIp())
            .setClientLocale(HttpRequestUtil.getLocale().getISO3Country())
            .setData(dataCollectReqDto.getData())
            .setSecretKey((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        try {
            respDto = dataCollectorService.collectData(collectedData);
        } catch (InterruptedException e) {
            log.error("데이터 수집 중 에러 발생!! " + e.getMessage());
        }
        return CommonResponse.of(respDto);
    }
}
