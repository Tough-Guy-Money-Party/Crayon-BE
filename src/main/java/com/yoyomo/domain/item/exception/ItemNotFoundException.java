package com.yoyomo.domain.item.exception;

import static com.yoyomo.domain.item.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.*;

import com.yoyomo.global.config.exception.ApplicationException;

public class ItemNotFoundException extends ApplicationException {
	public ItemNotFoundException() {
		super(NOT_FOUND.value(), ITEM_NOT_FOUND.getMessage());
	}
}
