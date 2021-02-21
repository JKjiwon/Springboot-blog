package com.jwsoft.blog.handler;

import com.jwsoft.blog.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice // @Controller 어노테이션을 가지거나, xml 설정 파일에서 컨트롤러로 명시된 클래스에서 Exception이 발생되면 이를 감지하겠다는 것이다.
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class) // Exception의 종류에 따라 분기하여 해당 처리를 하겠다.!!
    public ResponseDto<String> handleArgumentException(Exception e) {
        return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
