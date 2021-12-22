package io.hoon.datacollector.controller;

import io.hoon.datacollector.common.dto.CommonResponse;
import io.hoon.datacollector.dto.DataCollectReqDto;
import io.hoon.datacollector.dto.DataCollectRespDto;
import io.hoon.datacollector.repository.InMemoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "데이터 수집")
@RestController
@RequestMapping("/data")
public class DataCollectController {

    @PostMapping
    @Operation(summary = "데이터 수집 요청")
    public CommonResponse<DataCollectRespDto> collectRequest(//FIXME 요청 DTO 에 대한 Validation 이 누락되어있다.
                                                             @RequestBody DataCollectReqDto dataCollectReqDto) {
        InMemoryRepository.save(dataCollectReqDto);
        //FIXME 응답 데이터가 항상 성공이다.
        return CommonResponse.of(new DataCollectRespDto());
    }
}
