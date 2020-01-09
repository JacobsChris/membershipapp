package com.bae.member.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Name combination is not unique")
public class NonUniqueNameCombinationException extends Throwable {

}
