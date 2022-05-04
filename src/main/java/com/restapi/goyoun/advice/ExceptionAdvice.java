package com.restapi.goyoun.advice;
import com.restapi.goyoun.advice.exception.CAuthenticationEntryPointException;
import com.restapi.goyoun.advice.exception.CEmailSigninFailedException;
import com.restapi.goyoun.advice.exception.CUserNotFoundException;
import com.restapi.goyoun.model.response.CommonResult;
import com.restapi.goyoun.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RequiredArgsConstructor
@RestControllerAdvice // 예외발생시 json 형태로 결과 반환 / 프로젝트의 모든 Controller에 로직 적용
//RestControllerAdvice(basePackages="com.restapi.goyoun"): goyoun하위의 Controller 에만 로직 적용
public class ExceptionAdvice {
    private final ResponseService responseService; // 결과에 대한 정보를 도출하는 클래스 명시
    private final MessageSource messageSource;

    @ExceptionHandler(Exception.class) // Exception이 발생하면 해당 Handler로 처리하겠다고 명시하는 어노테이션 괄호안에는 어떤 Exception이 발생할때 적용할것인지
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Exception발생시 resource에 출력되는 httpstatus code가 500으로 내려가도록 설정
    protected CommonResult defaultException (HttpServletRequest request, Exception e) {
        //CommonResult : 응답 결과에 대한 정보
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
            // 예외처리 메시지를 MessageSource에서 가져오도록 수정, exception.ko.yml 파일에서 가져온 것임
            // getFailResult : setSuccess, setCode, setMsg
    }

    @ExceptionHandler(CUserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
        //CommonResult : 응답결과에 대한 정보
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")),getMessage("userNotFound.msg"));
        //getFailResult : setSuccess, setCode, setMsg
    }

    //code 정보에 해당하는 메시지를 조회한다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }

    //code 정보, 추가 argument로 현재 locale에 맞는 메시지를 조회한다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    @ExceptionHandler(CEmailSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSigninFailed(HttpServletRequest request, CEmailSigninFailedException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("emailSignFailed.code")),getMessage("emailSigninFailed.msg"));
    }

    @ExceptionHandler(CAuthenticationEntryPointException.class)
    public CommonResult authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
        return responseService.getFailResult((Integer.valueOf(getMessage("accessDenien.code"))), getMessage("accessDenied.msg"));
    }

}
