package com.wlkg.common.advice;

import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.common.pojo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {
    //该方法表示处理的异常类型
    @ExceptionHandler(WlkgException.class)
    public ResponseEntity<ExceptionResult> handleException(WlkgException e){
        ExceptionEnums exceptionEnums=e.getExceptionEnums();
        ExceptionResult result=new ExceptionResult(exceptionEnums);
        //我们暂定返回状态代码为400， 然后从异常中获取友好提示信息
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

