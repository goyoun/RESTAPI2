package com.restapi.goyoun.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListResult<T> extends CommonResult {
    // API 결과가 다중 건인 경우에 대한 데이터 모델
    // 결과 필드를 List형태로 선언하고 Generic Interface에 <T>를 지정하여 어떤형태의 List값도 넣을수 있도록 구현.

    private List<T> list;

}
