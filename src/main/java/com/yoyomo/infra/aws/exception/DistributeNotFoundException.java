package com.yoyomo.infra.aws.exception;

import static com.yoyomo.infra.aws.constant.ResponseMessage.*;

import org.springframework.http.HttpStatus;

import com.yoyomo.global.config.exception.ApplicationException;

public class DistributeNotFoundException extends ApplicationException {
	public DistributeNotFoundException() {
		super(HttpStatus.NOT_FOUND.value(), DISTRIBUTE_NOT_FOUND.getMessage());
	}
}
