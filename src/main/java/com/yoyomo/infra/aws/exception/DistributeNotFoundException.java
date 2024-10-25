package com.yoyomo.infra.aws.exception;

import static com.yoyomo.infra.aws.constant.ResponseMessage.DISTRIBUTE_NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;

import com.yoyomo.global.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DistributeNotFoundException extends ApplicationException {
    public DistributeNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(),DISTRIBUTE_NOT_FOUND.getMessage());
    }
}
