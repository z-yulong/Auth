package com.zyl.system.exception;

import com.zyl.common.result.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R resolveException(Exception e) {
        e.printStackTrace();
        return R.fail();
    }

    @ExceptionHandler(value = ArithmeticException.class)
    public R resolveArithmeticException(ArithmeticException e) {
        return R.fail().message("算数异常");
    }

    @ExceptionHandler(value = NullPointerException.class)
    public R resolveNullPointerException(NullPointerException e) {
        e.printStackTrace();
        return R.fail().message("空指针");
    }

    @ExceptionHandler(MyException.class)
    public R resolveMyException(MyException e) {
        e.printStackTrace();
        return R.fail().code(e.getCode()).message(e.getMessage());
    }

}
