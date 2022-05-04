package com.restapi.goyoun.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends CommonResult {
    //결과가 단일 건인 API를 담는 모델 Generic Interface에 <T>를 지정하여 어떤 형태의 값도 넣을수 있게함
    //CommonResult를 상속받아 API요청 결과도 같이 출력
    private T data;
}
