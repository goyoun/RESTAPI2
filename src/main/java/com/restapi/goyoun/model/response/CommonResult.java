package com.restapi.goyoun.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {


    // API의 실행 결과를 담는 공통 모델
    // success는 API의 성공 실패여부
    // code, msg는 해당 상황에서의 응답 코드와 응답 메세지.

    @ApiModelProperty(value="응답 성공여부 : true/false")
    private boolean success;

    @ApiModelProperty(value="응답코드번호 : >= 0 정상,  < 0 비정상")
    private int code;

    @ApiModelProperty(value="응답 메시지")
    private String msg;

}
