package com.bae.member.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.LENGTH_REQUIRED, reason = "The first name needs to be 2 or more letters long.")
public class MemberFirstNameTooShortException extends RuntimeException {
}
