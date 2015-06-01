package com.lumira.model.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.lumira.model.ApplicationToken;

/**
 * Validation class that ensures required attributes are present on {@link ApplicationToken} objects that are consumed
 * by our controllers.
 * 
 * @author jon
 * 
 */
public class ApplicationTokenValidator implements Validator
{

    @Override
    public boolean supports(Class<?> clazz)
    {
        return ApplicationToken.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors)
    {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "connectKey", "connect_key must not be null");
    }
}
