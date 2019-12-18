package com.bae.gathering.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.LENGTH_REQUIRED, reason = "The location must be 58 characters or shorter.")
public class LocationTooLongException extends RuntimeException {
}
