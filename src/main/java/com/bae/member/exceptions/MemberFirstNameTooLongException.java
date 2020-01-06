package com.bae.member.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.LENGTH_REQUIRED, reason = "The first name needs to be 25 letters or fewer.")
public class MemberFirstNameTooLongException extends RuntimeException {
}
