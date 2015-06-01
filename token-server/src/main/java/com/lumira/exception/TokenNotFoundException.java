package com.lumira.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that is thrown when a token is not found.
 * 
 * @author jon
 * 
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such token")
public class TokenNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = -8790211652911971729L;

    public TokenNotFoundException(String token)
    {
        super(token + " not found");
    }
}